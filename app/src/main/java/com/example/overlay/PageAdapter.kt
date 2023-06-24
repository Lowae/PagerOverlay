package com.example.overlay

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.overlay.databinding.ItemPageBinding

class PageAdapter(val items: List<PageItem>) : RecyclerView.Adapter<PageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(
            ItemPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.vb.img.setImageDrawable(items[position].drawable)
        holder.vb.id.text = items[position].id
    }
}

class PageViewHolder(val vb: ItemPageBinding) : RecyclerView.ViewHolder(vb.root)

class PageItem(
    val id: String,
    val drawable: Drawable?
) {
}