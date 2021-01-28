package com.task.ui.component.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.R
import com.task.data.dto.image.ImageItem
import kotlinx.android.synthetic.main.image_item.view.*

class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: ImageItem?) {
        item?.let {
            view.apply {
                Glide.with(itemView.context)
                    .load(it.downloadUrl)
                    .apply(
                        RequestOptions().placeholder(R.drawable.background_splash)
                            .error(R.drawable.background_splash)
                    )
                    .into(image_view)

            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item, parent, false)
            return ImageViewHolder(view)
        }
    }
}