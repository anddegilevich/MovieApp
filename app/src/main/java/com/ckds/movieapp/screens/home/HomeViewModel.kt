package com.ckds.movieapp.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    var page = 1

    init {
        getPopularMovies()
    }

    fun getPopularMovies() = viewModelScope.launch {
        val response = repository.getPopularMovies()
        if (response.isSuccessful) {
            val movies = response.body()?.movies ?: emptyList()
            popularMovies.postValue(popularMovies.value?.plus(movies) ?: movies)
        }
    }


}