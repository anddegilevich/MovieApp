package com.ckds.movieapp.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ckds.movieapp.R
import kotlinx.android.synthetic.main.item_search.view.*

class SearchMoviesAdapter: MoviesAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.itemView.apply {
            tv_name.text = movie.title

            setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }
    }

}