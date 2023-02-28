package com.ckds.movieapp.data.api

import com.ckds.movieapp.data.model.MovieResponse
import com.ckds.movieapp.data.model.SeriesResponse
import com.ckds.movieapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    //Movie
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
        @Query("format") format: String = "json",
    ) : Response<MovieResponse>

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
        @Query("query") query: String,
        @Query("page") page: Int? = null,
        @Query("include_adult") includeAdult: Boolean? = null,
        @Query("region") region: String? = null,
        @Query("year") year: Int? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("format") format: String = "json",
    ) : Response<MovieResponse>

    //Series
    @GET("/3/tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("format") format: String = "json",
    ) : Response<SeriesResponse>

    @GET("/3/search/tv")
    suspend fun searchSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("format") format: String = "json",
    ) : Response<SeriesResponse>

}