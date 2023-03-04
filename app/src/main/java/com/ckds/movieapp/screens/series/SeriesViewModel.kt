package com.ckds.movieapp.screens.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.credits.CreditsResponse
import com.ckds.movieapp.data.model.details.series.SeriesDetailsResponse
import com.ckds.movieapp.data.model.series.Series
import com.ckds.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val detailsLiveData: MutableLiveData<Resource<SeriesDetailsResponse>> = MutableLiveData()
    val creditsLiveData: MutableLiveData<Resource<CreditsResponse>> = MutableLiveData()

    fun getDetails(tvId: Int) = viewModelScope.launch {
        detailsLiveData.postValue(Resource.Loading())
        try {
            val response = repository.getSeriesDetails(tvId = tvId)
            detailsLiveData.postValue(Resource.Success(data = response))
        } catch (throwable: Throwable) {
            detailsLiveData.postValue(Resource.Error(throwable = throwable))
        }
    }

    fun getCredits(tvId: Int) = viewModelScope.launch {
        creditsLiveData.postValue(Resource.Loading())
        try {
            val response = repository.getSeriesCredits(tvId = tvId)
            creditsLiveData.postValue(Resource.Success(data = response))
        } catch (throwable: Throwable) {
            creditsLiveData.postValue(Resource.Error(throwable = throwable))
        }
    }

    fun addSeriesToFavourite(series: Series) {
        viewModelScope.launch {
            repository.addSeriesToFavourite(series = series)
        }
    }

    fun deleteSeriesFromFavourite(tvId: Int) {
        viewModelScope.launch {
            repository.deleteSeriesFromFavourite(tvId = tvId)
        }
    }

    suspend fun checkIfSeriesIsFavorite(tvId: Int) =
        repository.checkIfSeriesIsFavorite(tvId = tvId)

}