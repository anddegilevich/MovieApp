package com.ckds.movieapp.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularSeries: MutableLiveData<List<Series>> = MutableLiveData()

    init {
        getPopularMovies()
        getPopularSeries()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        val response = repository.getPopularMovies()
        if (response.isSuccessful) {
            val movies = response.body()?.movies?.subList(0,9) ?: emptyList()
            popularMovies.postValue(popularMovies.value?.plus(movies) ?: movies)
        }
    }

    private fun getPopularSeries() = viewModelScope.launch {
        val response = repository.getPopularSeries()
        if (response.isSuccessful) {
            val series = response.body()?.series?.subList(0,9) ?: emptyList()
            popularSeries.postValue(popularSeries.value?.plus(series) ?: series)
        }
    }


}