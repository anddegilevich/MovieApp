package com.ckds.movieapp.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ckds.movieapp.R
import com.ckds.movieapp.data.model.Movie
import com.ckds.movieapp.data.model.Series
import com.ckds.movieapp.utils.Constants
import kotlinx.android.synthetic.main.item_poster.view.*

class SeriesAdapter: RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    private val callback = object : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val series = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load("${Constants.POSTER_BASE_URL}${series.poster_path}")
                .error(R.drawable.no_image_sample).into(img_poster)
            img_poster.clipToOutline = true
            tv_name.text = series.name
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Series) -> Unit)? = null

    fun setOnItemClickListener(listener: (Series) -> Unit) {
        onItemClickListener = listener
    }
}