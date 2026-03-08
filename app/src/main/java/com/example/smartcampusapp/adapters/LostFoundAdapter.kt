package com.example.smartcampusapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcampusapp.databinding.ItemLostfoundBinding
import com.example.smartcampusapp.models.LostFoundItem
import com.squareup.picasso.Picasso

class LostFoundAdapter(private val items: List<LostFoundItem>) : RecyclerView.Adapter<LostFoundAdapter.VH>() {
    inner class VH(val binding: ItemLostfoundBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemLostfoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.binding.tvType.text = it.type
        holder.binding.tvDesc.text = it.description
        holder.binding.tvContact.text = it.contact

        val img = it.imageUriString
        if (img.isNotEmpty()) {
            // If it's a remote URL, use Picasso to load. If content:// uri, Picasso also handles it.
            Picasso.get().load(img).fit().centerCrop().into(holder.binding.ivItem)
        } else {
            holder.binding.ivItem.setImageResource(android.R.drawable.ic_menu_report_image)
        }
    }
    override fun getItemCount() = items.size
}