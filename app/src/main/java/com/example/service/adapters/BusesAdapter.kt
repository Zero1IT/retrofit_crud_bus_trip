package com.example.service.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.service.beans.BusTrip
import com.example.service.databinding.BusListItemBinding

class BusesAdapter(private val callback: (bus: BusTrip) -> Unit)
    : ListAdapter<BusTrip, BusesAdapter.BusTripHolder>(BusTripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusTripHolder = BusTripHolder.from(parent)

    override fun onBindViewHolder(holder: BusTripHolder, position: Int) = holder.bind(getItem(position), callback)

    fun removeAt(adapterPosition: Int): BusTrip {
        val list = mutableListOf<BusTrip>()
        list.addAll(currentList)
        val removed = list.removeAt(adapterPosition)
        submitList(list)
        return removed
    }

    class BusTripHolder private constructor(private val binding: BusListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(bus: BusTrip, callback: (bus: BusTrip) -> Unit) {
            binding.root.setOnClickListener {
                callback(bus)
            }
            binding.destination.text = bus.destinationTitle
            binding.number.text = bus.routeNumber.toString()
            binding.time.text = "${bus.departureTime} - ${bus.arrivalTime}"
        }

        companion object {
            fun from(parent: ViewGroup): BusTripHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = BusListItemBinding.inflate(inflater, parent, false)
                return BusTripHolder(bind)
            }
        }
    }

    class BusTripDiffCallback : DiffUtil.ItemCallback<BusTrip>() {
        override fun areItemsTheSame(oldItem: BusTrip, newItem: BusTrip): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BusTrip, newItem: BusTrip): Boolean {
            return oldItem == newItem
        }
    }
}