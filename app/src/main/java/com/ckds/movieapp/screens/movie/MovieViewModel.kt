package com.ckds.movieapp.screens.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.credits.Cast
import com.ckds.movieapp.data.model.details.movie.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val detailsLiveData: MutableLiveData<MovieDetailsResponse> = MutableLiveData()
    val castLiveData: MutableLiveData<List<Cast>> = MutableLiveData()

    fun getDetails(movieId: Int) = viewModelScope.launch {
        val response = repository.getMovieDetails(movieId = movieId)
        if (response.isSuccessful) {
            response.body().let {  details ->
                detailsLiveData.postValue(details)
            }
        }
    }

    fun getCast(movieId: Int) = viewModelScope.launch {
        val response = repository.getMovieCredits(movieId = movieId)
        if (response.isSuccessful) {
            response.body()?.cast.let { cast ->
                castLiveData.postValue(cast)
            }
        }
    }

}