package com.ckds.movieapp.screens.adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdapterViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun addMovieToFavourite(movie: Movie) {
        viewModelScope.launch {
            repository.addMovieToFavourite(movie = movie)
        }
    }

    fun deleteMovieFromFavourite(movieId: Int) {
        viewModelScope.launch {
            repository.deleteMovieFromFavourite(movieId = movieId)
        }
    }

    suspend fun checkIfMovieIsFavorite(movieId: Int) =
        repository.checkIfMovieIsFavorite(movieId = movieId)

    fun addSeriesToFavourite(series: Series) {
        viewModelScope.launch {
            repository.addSeriesToFavourite(series = series)
        }
    }

    fun deleteSeriesFromFavourite(tvId: Int) {
        viewModelScope.launch {
            repository.deleteSeriesFromFavourite(tvId = tvId)
        }
    }

    suspend fun checkIfSeriesIsFavorite(tvId: Int) =
        repository.checkIfSeriesIsFavorite(tvId = tvId)

}