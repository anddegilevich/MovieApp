package com.ckds.movieapp.screens.search

import androidx.lifecycle.*
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val searchedMovies: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val searchedSeries: MutableLiveData<Resource<List<Series>>> = MutableLiveData()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            searchedMovies.postValue(Resource.Loading())
            try {
                val response = repository.searchMovies(query = query)
                searchedMovies.postValue(Resource.Success(data = response))
            } catch (throwable: Throwable) {
                searchedMovies.postValue(Resource.Error(throwable = throwable))
            }
        }
    }

    fun searchSeries(query: String) = viewModelScope.launch {
        viewModelScope.launch {
            searchedSeries.postValue(Resource.Loading())
            try {
                val response = repository.searchSeries(query = query)
                searchedSeries.postValue(Resource.Success(data = response))
            } catch (throwable: Throwable) {
                searchedSeries.postValue(Resource.Error(throwable = throwable))
            }
        }
    }
}