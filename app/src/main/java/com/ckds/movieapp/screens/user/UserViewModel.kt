package com.ckds.movieapp.screens.user

import androidx.lifecycle.ViewModel
import com.ckds.movieapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun checkSessionState() = repository.getSessionState()

    suspend fun authenticate(login: String, password: String) =
        repository.authenticate(login = login, password = password)
}