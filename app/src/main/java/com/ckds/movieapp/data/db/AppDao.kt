package com.ckds.movieapp.data.db

import androidx.room.*
import androidx.room.Dao
import com.ckds.movieapp.data.db.entities.StoredMovies
import com.ckds.movieapp.data.db.entities.StoredSeries
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    //MoviesId

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopularMoviesId(moviesId: List<StoredMovies>)

    @Query("DELETE FROM stored_movies WHERE category = :category")
    fun deleteStoredMoviesIds(category: String)

    //Movies

    @Query("""SELECT * FROM movies WHERE id IN (SELECT id FROM stored_movies WHERE category = :category) ORDER BY popularity DESC""")
    fun getStoredMovies(category: String) : Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT DISTINCT id FROM stored_movies)")
    fun deleteUnusedMovies()

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%'")
    suspend fun searchMovies(query: String) : List<Movie>

    //SeriesId

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopularSeriesId(tvId: List<StoredSeries>)

    @Query("DELETE FROM stored_series WHERE category = :category")
    fun deleteStoredSeriesIds(category: String)

    //Series

    @Query("""SELECT * FROM series WHERE id IN (SELECT id FROM stored_series WHERE category = :category) ORDER BY popularity DESC""")
    fun getStoredSeries(category: String) : Flow<List<Series>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<Series>)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT DISTINCT id FROM stored_movies)")
    fun deleteUnusedSeries()

    @Query("SELECT * FROM series WHERE name LIKE '%' || :query || '%'")
    suspend fun searchSeries(query: String) : List<Series>

}