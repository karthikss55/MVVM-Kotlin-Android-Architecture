package com.task.ui.component.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import kotlinx.android.synthetic.main.layout_loader.view.*

class LoadingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind() {
        view.apply {
            progress_bar.visibility = View.VISIBLE

        }
    }

    companion object {
        fun create(parent: ViewGroup): LoadingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_loader, parent, false)
            return LoadingViewHolder(view)
        }
    }
}