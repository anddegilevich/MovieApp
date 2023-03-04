package com.ckds.movieapp.screens.user

import androidx.lifecycle.*
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.movie.Movie
import com.ckds.movieapp.data.model.series.Series
import com.ckds.movieapp.data.model.user.UserDetailsResponse
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    lateinit var favoriteMovies: LiveData<Resource<List<Movie>>>
    lateinit var favoriteSeries: LiveData<Resource<List<Series>>>
    val detailsLiveData: MutableLiveData<Resource<UserDetailsResponse>> = MutableLiveData()

    fun checkSessionState() = repository.getSessionState()

    suspend fun authenticate(login: String, password: String) =
        repository.authenticate(login = login, password = password)

    fun logOut() = viewModelScope.launch {
        repository.logOut()
    }

    fun getFavoriteMovies() {
        favoriteMovies = repository.getFavoriteMovies().asLiveData()
    }

    fun getFavoriteSeries() {
        favoriteSeries = repository.getFavoriteSeries().asLiveData()
    }

    fun getDetails() = viewModelScope.launch {
        detailsLiveData.postValue(Resource.Loading())
        try {
            val response = repository.getAccountDetails()
            detailsLiveData.postValue(Resource.Success(data = response))
        } catch (throwable: Throwable) {
            detailsLiveData.postValue(Resource.Error(throwable = throwable))
        }
    }
}