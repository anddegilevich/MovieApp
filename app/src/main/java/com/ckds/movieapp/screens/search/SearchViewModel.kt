package com.ckds.movieapp.screens.search

import androidx.lifecycle.*
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val searchedMovies: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val searchedSeries: MutableLiveData<Resource<List<Series>>> = MutableLiveData()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query = query).collect{ resource ->
                searchedMovies.postValue(resource)
            }
        }
    }

    fun searchSeries(query: String) = viewModelScope.launch {
        viewModelScope.launch {
            repository.searchSeries(query = query).collect{ resource ->
                searchedSeries.postValue(resource)
            }
        }
    }
}