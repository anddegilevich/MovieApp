package com.ckds.movieapp.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ckds.movieapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository): ViewModel() {


}