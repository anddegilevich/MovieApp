package com.ckds.movieapp.screens.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckds.movieapp.data.Repository
import com.ckds.movieapp.data.model.credits.Cast
import com.ckds.movieapp.data.model.details.series.SeriesDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val detailsLiveData: MutableLiveData<SeriesDetailsResponse> = MutableLiveData()
    val castLiveData: MutableLiveData<List<Cast>> = MutableLiveData()

    fun getDetails(tvId: Int) = viewModelScope.launch {
        val response = repository.getSeriesDetails(tvId = tvId)
        if (response.isSuccessful) {
            response.body().let { details ->
                detailsLiveData.postValue(details)
            }
        }
    }

    fun getCast(tvId: Int) = viewModelScope.launch {
        val response = repository.getSeriesCredits(tvId = tvId)
        if (response.isSuccessful) {
            response.body()?.cast.let { cast ->
                castLiveData.postValue(cast)
            }
        }
    }

}