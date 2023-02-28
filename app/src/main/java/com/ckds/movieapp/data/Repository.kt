package com.ckds.movieapp.data

import com.ckds.movieapp.data.api.Service
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: Service
) {

    //Movie
    suspend fun getPopularMovies(page: Int? = null) =
        service.getPopularMovies(page = page)

    suspend fun searchMovies(query: String, page: Int? = null) =
        service.searchMovies(query = query, page = page)

    //Series
    suspend fun getPopularSeries(page: Int? = null) =
        service.getPopularSeries(page = page)

    suspend fun searchSeries(query: String, page: Int? = null) =
        service.searchSeries(query = query, page = page)
}