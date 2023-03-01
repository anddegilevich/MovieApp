package com.ckds.movieapp.data.model.series

import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    val page: Int,
    @SerializedName("results") val series: List<Series>,
    val total_pages: Int,
    val total_results: Int
)