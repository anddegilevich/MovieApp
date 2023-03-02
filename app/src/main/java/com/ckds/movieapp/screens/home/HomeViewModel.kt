package com.ckds.movieapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ckds.movieapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val popularMovies = repository.getPopularMovies().asLiveData()
    val popularSeries = repository.getPopularSeries().asLiveData()

}