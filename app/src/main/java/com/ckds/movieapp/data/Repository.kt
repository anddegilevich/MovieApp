package com.ckds.movieapp.data

import androidx.room.withTransaction
import com.ckds.movieapp.data.api.AppApi
import com.ckds.movieapp.data.db.AppDatabase
import com.ckds.movieapp.data.db.entities.StoredMovies
import com.ckds.movieapp.data.db.entities.StoredSeries
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
            appApi.getPopularMovies().movies
        },
        saveFetchResult = { movies ->
            appDb.withTransaction {
                appDao.deleteStoredMoviesIds("popular")
                appDao.insertPopularMoviesId(movies.map {
                    StoredMovies(id = it.id, category = "popular"
                ) })
                appDao.insertMovies(movies)
                appDao.deleteUnusedMovies()
            }
        }
    )

    fun searchMovies(query: String, page: Int? = null) = networkBoundResource(
        query = {
            appDao.searchMovies(query = query)
        },
        fetch = {
            delay(1000)
            appApi.searchMovies(query = query, page = page).movies
        },
        saveFetchResult = {}
    )

    suspend fun getMovieDetails(movieId: Int) = appApi.getMovieDetails(movieId = movieId)

    suspend fun getMovieCredits(movieId: Int) = appApi.getMovieCredits(movieId = movieId)

    //Series

    fun getPopularSeries() = networkBoundResource(
        query = {
            appDao.getStoredSeries("popular")
        },
        fetch = {
            delay(1000)
            appApi.getPopularSeries().series
        },
        saveFetchResult = { series ->
            appDb.withTransaction {
                appDao.deleteStoredSeriesIds("popular")
                appDao.insertPopularSeriesId(series.map {
                    StoredSeries(id = it.id, category = "popular"
                    ) })
                appDao.insertSeries(series)
                appDao.deleteUnusedSeries()
            }
        }
    )

    fun searchSeries(query: String, page: Int? = null) = networkBoundResource(
        query = {
            appDao.searchSeries(query = query)
        },
        fetch = {
            delay(1000)
            appApi.searchSeries(query = query, page = page).series
        },
        saveFetchResult = {}
        )

    suspend fun getSeriesDetails(tvId: Int) =
        appApi.getSeriesDetails(tvId = tvId)

    suspend fun getSeriesCredits(tvId: Int) =
        appApi.getSeriesCredits(tvId = tvId)


}