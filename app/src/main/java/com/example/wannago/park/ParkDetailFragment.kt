package com.example.wannago.park

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wannago.databinding.FragmentParkDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ParkDetailFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentParkDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "binding cannot be created. Is view created?"
        }

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentParkDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailMapView.onCreate(savedInstanceState)
        binding.detailMapView.getMapAsync(this)

        val args = ParkDetailFragmentArgs.fromBundle(requireArguments())
        val latitude = args.latitude
        val longitude = args.longitude
        binding.latitudeTextView.text = "Latitude: $latitude"
        binding.longitudeTextView.text = "Longitude: $longitude"
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        val args = ParkDetailFragmentArgs.fromBundle(requireArguments())
        val latitude = args.latitude.toDouble()
        val longitude = args.longitude.toDouble()
        val initialLocation = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f))
        map.addMarker(MarkerOptions().position(initialLocation).title("Marker Title"))

    }

    override fun onResume() {
        super.onResume()
        binding.detailMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.detailMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
