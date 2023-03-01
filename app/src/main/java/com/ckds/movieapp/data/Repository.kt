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

    suspend fun getMovieGenres() =
        service.getMovieGenres()

    suspend fun getMovieDetails(movieId: Int) =
        service.getMovieDetails(movieId = movieId)

    suspend fun getMovieCredits(movieId: Int) =
        service.getMovieCredits(movieId = movieId)

    //Series
    suspend fun getPopularSeries(page: Int? = null) =
        service.getPopularSeries(page = page)

    suspend fun searchSeries(query: String, page: Int? = null) =
        service.searchSeries(query = query, page = page)

    suspend fun getSeriesGenres() =
        service.getSeriesGenres()

    suspend fun getSeriesDetails(tvId: Int) =
        service.getSeriesDetails(tvId = tvId)

    suspend fun getSeriesCredits(tvId: Int) =
        service.getSeriesCredits(tvId = tvId)
}