package  com.example.wannago

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wannago.park.ParkMarker
import com.example.wannago.rest.RestMarker
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

private const val TAG = "SharedViewModel"

class SharedViewModel: ViewModel() {

    private val db = Firebase.firestore

    private val _restList = MutableStateFlow<List<RestMarker>>(emptyList())
    val restList: StateFlow<List<RestMarker>> get() = _restList

    private val _parkList = MutableStateFlow<List<ParkMarker>>(emptyList())
    val parkList: StateFlow<List<ParkMarker>> get() = _parkList

    fun write(collectionName: String, latitude: Double, longitude: Double) {
        val user = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )

        db.collection(collectionName)
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun read(collectionName: String) {
        db.collection(collectionName)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed", e)
                    return@addSnapshotListener
                } else {
                    when (collectionName) {
                        "location" -> {
                            _restList.value = snapshot?.documents?.mapNotNull { doc ->
                                val latitude = doc["latitude"] as? Double
                                val longitude = doc["longitude"] as? Double
                                if (latitude != null && longitude != null) {
                                    RestMarker(latitude, longitude)
                                } else {
                                    null
                                }
                            } ?: emptyList()
                        }
                        "park" -> {
                            _parkList.value = snapshot?.documents?.mapNotNull { doc ->
                                val latitude = doc["latitude"] as? Double
                                val longitude = doc["longitude"] as? Double
                                if (latitude != null && longitude != null) {
                                    ParkMarker(latitude, longitude)
                                } else {
                                    null
                                }
                            } ?: emptyList()
                        }
                    }
                }
            }
    }

    suspend fun getExistingLocations(collectionName: String): List<LatLng> {
        val existingLocations = mutableListOf<LatLng>()

        val collection = db.collection(collectionName)
        val querySnapshot = collection.get().await()

        for (document in querySnapshot.documents) {
            val latitude = document.getDouble("latitude") ?: 0.0
            val longitude = document.getDouble("longitude") ?: 0.0

            existingLocations.add(LatLng(latitude, longitude))
        }

        return existingLocations
    }
}
