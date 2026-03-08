package com.example.smartcampusapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcampusapp.databinding.ItemEventBinding
import com.example.smartcampusapp.models.Event

class EventAdapter(private val items: List<Event>) : RecyclerView.Adapter<EventAdapter.VH>() {
    inner class VH(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val e = items[position]
        holder.binding.tvTitle.text = e.title
        holder.binding.tvDescription.text = e.description
        holder.binding.tvDateTime.text = e.date
    }

    override fun getItemCount() = items.size
}