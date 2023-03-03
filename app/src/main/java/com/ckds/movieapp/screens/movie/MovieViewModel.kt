package com.ckds.movieapp.screens.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.credits.Cast
import com.ckds.movieapp.data.model.credits.CreditsResponse
import com.ckds.movieapp.data.model.details.movie.MovieDetailsResponse
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val detailsLiveData: MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()
    val creditsLiveData: MutableLiveData<Resource<CreditsResponse>> = MutableLiveData()

    fun getDetails(movieId: Int) = viewModelScope.launch {
        viewModelScope.launch {
            detailsLiveData.postValue(Resource.Loading())
            try {
                val response = repository.getMovieDetails(movieId = movieId)
                detailsLiveData.postValue(Resource.Success(data = response))
            } catch (throwable: Throwable) {
                detailsLiveData.postValue(Resource.Error(throwable = throwable))
            }
        }
    }

    fun getCredits(movieId: Int) = viewModelScope.launch {
        viewModelScope.launch {
            creditsLiveData.postValue(Resource.Loading())
            try {
                val response = repository.getMovieCredits(movieId = movieId)
                creditsLiveData.postValue(Resource.Success(data = response))
            } catch (throwable: Throwable) {
                creditsLiveData.postValue(Resource.Error(throwable = throwable))
            }
        }
    }

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


}