package com.ckds.movieapp.data

import androidx.room.withTransaction
import com.ckds.movieapp.data.api.AppApi
import com.ckds.movieapp.data.db.AppDatabase
import com.ckds.movieapp.data.db.entities.StoredMovies
import com.ckds.movieapp.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class Repository @Inject constructor(
    private val appApi: AppApi,
    private val appDb: AppDatabase
) {

    private val appDao = appDb.getDao()

    //Movie

    fun getPopularMovies() = networkBoundResource(
        query = {
            appDao.getStoredMovies("popular")
        },
        fetch = {
            delay(1000)
            appApi.getPopularMovies()
        },
        saveFetchResult = { response ->
            appDb.withTransaction {
                appDao.deleteStoredMoviesIds("popular")
                appDao.insertPopularMoviesId(response.movies.map {
                    StoredMovies(id = it.id, category = "popular"
                ) })
                appDao.insertMovies(response.movies)
                appDao.deleteUnusedMovies()
            }
        }
    )

    suspend fun searchMovies(query: String, page: Int? = null) =
        appApi.searchMovies(query = query, page = page)

    suspend fun getMovieGenres() =
        appApi.getMovieGenres()

    suspend fun getMovieDetails(movieId: Int) =
        appApi.getMovieDetails(movieId = movieId)

    suspend fun getMovieCredits(movieId: Int) =
        appApi.getMovieCredits(movieId = movieId)

    //Series
    suspend fun getPopularSeries(page: Int? = null) =
        appApi.getPopularSeries(page = page)

    suspend fun searchSeries(query: String, page: Int? = null) =
        appApi.searchSeries(query = query, page = page)

    suspend fun getSeriesGenres() =
        appApi.getSeriesGenres()

    suspend fun getSeriesDetails(tvId: Int) =
        appApi.getSeriesDetails(tvId = tvId)

    suspend fun getSeriesCredits(tvId: Int) =
        appApi.getSeriesCredits(tvId = tvId)


}