package com.ckds.movieapp.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.series.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val popularMovies = repository.getPopularMovies().asLiveData()
    val popularSeries: MutableLiveData<List<Series>> = MutableLiveData()

    init {
        //getPopularSeries()
    }

    private fun getPopularSeries() = viewModelScope.launch {
        val response = repository.getPopularSeries()
        if (response.isSuccessful) {
            response.body()?.series?.subList(0,9).let { series ->
                popularSeries.postValue(series)
            }
        }
    }

}