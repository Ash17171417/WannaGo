package com.example.wannago.rest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wannago.databinding.RestListItemBinding
import com.google.android.gms.maps.model.LatLng

class RestAdapter(private val onItemClick: (LatLng) -> Unit): RecyclerView.Adapter<RestAdapter.RestViewHolder>() {
    private var restmarkers: List<RestMarker> = emptyList()

    fun submitList(restmarkers: List<RestMarker>) {
        this.restmarkers = restmarkers
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RestListItemBinding.inflate(inflater, parent, false)
        return RestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestViewHolder, position: Int) {
        val restmarker = restmarkers[position]
        holder.bind(restmarker)
        holder.itemView.setOnClickListener {
            val location = LatLng(restmarker.latitude, restmarker.longitude)
            onItemClick.invoke(location)
        }
    }

    override fun getItemCount() = restmarkers.size

    inner class RestViewHolder(private val binding: RestListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restMarker: RestMarker) {
            binding.restLatitude.text = "latitude: ${restMarker.latitude}"
            binding.restLongitude.text = "longitude ${restMarker.longitude}"
        }
    }
}