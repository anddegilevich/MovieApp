package com.ckds.movieapp.data.db

import androidx.room.*
import androidx.room.Dao
import com.ckds.movieapp.data.db.entities.StoredMovies
import com.ckds.movieapp.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("""SELECT * FROM movies WHERE id IN (SELECT id FROM stored_movies WHERE category = :category) ORDER BY popularity DESC""")
    fun getStoredMovies(category: String) : Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMoviesId(moviesId: List<StoredMovies>)

    @Query("DELETE FROM stored_movies WHERE category = :category")
    fun deleteStoredMoviesIds(category: String)


    //Movies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movies WHERE id NOT IN (SELECT DISTINCT id FROM stored_movies)")
    fun deleteUnusedMovies()

}