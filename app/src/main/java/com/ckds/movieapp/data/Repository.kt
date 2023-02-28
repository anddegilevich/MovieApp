package com.ckds.movieapp.data

import com.ckds.movieapp.data.api.Service
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: Service
) {

    //Movie
    suspend fun getPopularMovies(page: Int = 1) =
        service.getPopularMovies(page = page)

}