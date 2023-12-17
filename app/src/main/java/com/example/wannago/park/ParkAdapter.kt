package com.example.wannago.park

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wannago.databinding.ParkListItemBinding

class ParkAdapter(private val onItemClick: (ParkMarker) -> Unit): RecyclerView.Adapter<ParkAdapter.ParkViewHolder>() {
    private var parkmarkers: List<ParkMarker> = emptyList()

    fun submitList(parkmarkers: List<ParkMarker>) {
        this.parkmarkers = parkmarkers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParkListItemBinding.inflate(inflater, parent, false)
        return ParkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val parkmarker = parkmarkers[position]
        holder.bind(parkmarker)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(parkmarker)
        }
    }

    override fun getItemCount() = parkmarkers.size

    inner class ParkViewHolder(private val binding: ParkListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(parkMarker: ParkMarker) {
            binding.parkLatitude.text = "latitude: ${parkMarker.latitude}"
            binding.parkLongitude.text = "longitude ${parkMarker.longitude}"
        }
    }
}