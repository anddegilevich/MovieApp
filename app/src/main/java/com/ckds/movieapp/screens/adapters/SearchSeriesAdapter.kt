package com.ckds.movieapp.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ckds.movieapp.R
import kotlinx.android.synthetic.main.item_poster.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.item_search.view.checkbox_favorite
import kotlinx.android.synthetic.main.item_search.view.tv_name
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchSeriesAdapter(override val viewModel: AdapterViewModel) : SeriesAdapter(viewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val series = differ.currentList[position]

        holder.itemView.apply {
            tv_name.text = series.name

            setOnClickListener {
                onItemClickListener?.let { it(series) }
            }
            MainScope().launch {
                val favorite = MainScope().async {viewModel.checkIfSeriesIsFavorite(series.id!!)}
                checkbox_favorite.isChecked = favorite.await()
            }
            checkbox_favorite.setOnCheckedChangeListener{ _, isChecked ->
                if (isChecked) {
                    viewModel.addSeriesToFavourite(series = series)
                } else {
                    viewModel.deleteSeriesFromFavourite(tvId = series.id!!)
                }
            }
        }
    }

}