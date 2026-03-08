package com.example.smartcampusapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcampusapp.databinding.ItemNoticeBinding
import com.example.smartcampusapp.models.Notice

class NoticeAdapter(private val items: List<Notice>) : RecyclerView.Adapter<NoticeAdapter.VH>() {
    inner class VH(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val n = items[position]
        holder.binding.tvTitle.text = n.title
        holder.binding.tvDesc.text = n.description
        holder.binding.tvDate.text = n.date
    }

    override fun getItemCount(): Int = items.size
}