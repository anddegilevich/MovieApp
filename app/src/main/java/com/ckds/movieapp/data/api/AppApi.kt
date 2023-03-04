package com.ckds.movieapp.data.api

import com.ckds.movieapp.data.model.auth.AuthRequest
import com.ckds.movieapp.data.model.auth.SessionResponse
import com.ckds.movieapp.data.model.credits.CreditsResponse
import com.ckds.movieapp.data.model.details.movie.MovieDetailsResponse
import com.ckds.movieapp.data.model.details.series.SeriesDetailsResponse
import com.ckds.movieapp.data.model.movie.MovieResponse
import com.ckds.movieapp.data.model.series.SeriesResponse
import com.ckds.movieapp.data.model.auth.TokenResponse
import com.ckds.movieapp.data.model.user.FavoriteRequest
import com.ckds.movieapp.utils.Constants.Companion.API_KEY
import retrofit2.http.*

interface AppApi {

    //Movie

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
        @Query("region") region: String? = null,
    ) : MovieResponse

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
    ) : MovieResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
        @Query("append_to_response") appendToResponse: String? = null,
    ) : MovieDetailsResponse

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
    ) : CreditsResponse

    //Series

    @GET("/3/tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int? = null,
        @Query("language") language: String? = null,
    ) : SeriesResponse

    @GET("/3/search/tv")
    suspend fun searchSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
    ) : SeriesResponse

    @GET("/3/tv/{tv_id}")
    suspend fun getSeriesDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
        @Query("append_to_response") appendToResponse: String? = null,
    ) : SeriesDetailsResponse

    @GET("/3/tv/{tv_id}/credits")
    suspend fun getSeriesCredits(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String? = null,
    ) : CreditsResponse

    // Auth

    @GET("/3/authentication/token/new")
    suspend fun getAuthenticationToken(
        @Query("api_key") apiKey: String = API_KEY,
    ) : TokenResponse

    @POST("/3/authentication/token/validate_with_login")
    suspend fun authenticateToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body authRequest: AuthRequest,
    ) : TokenResponse

    @POST("/3/authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body requestToken: TokenResponse,
    ) : SessionResponse

    @HTTP(method = "DELETE", path = "/3/authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionId: SessionResponse,
    )

    // User

    /*@GET("/3/account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
    ) : AccountDetailsResponse*/

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("/3/account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
        @Body favoriteRequest: FavoriteRequest
    )

    @GET("/3/account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
        @Query("language") language: String? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("page") page: Int? = null,
    ) : MovieResponse

    @GET("/3/account/{account_id}/favorite/tv")
    suspend fun getFavoriteSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") sessionId: String,
        @Query("language") language: String? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("page") page: Int? = null,
    ) : SeriesResponse

}