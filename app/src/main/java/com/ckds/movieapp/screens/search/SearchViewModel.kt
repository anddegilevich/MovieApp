package com.ckds.movieapp.screens.search

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
class SearchViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val searchedMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val searchedSeries: MutableLiveData<List<Series>> = MutableLiveData()

    fun searchMovies(query: String) = viewModelScope.launch {
        val response = repository.searchMovies(query = query)
        if (response.isSuccessful) {
            response.body()?.movies?.let{ movies ->
                searchedMovies.postValue(movies)
            }
        }
    }

    fun searchSeries(query: String) = viewModelScope.launch {
        val response = repository.searchSeries(query)
        if (response.isSuccessful) {
            response.body()?.series?.let { series ->
                searchedSeries.postValue(series)
            }
        }
    }
}