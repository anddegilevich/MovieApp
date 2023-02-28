package com.ckds.movieapp.data.api

import com.ckds.movieapp.data.model.MovieResponse
import com.ckds.movieapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    //Chart

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
        @Query("format") format: String = "json",
    ) : Response<MovieResponse>


}