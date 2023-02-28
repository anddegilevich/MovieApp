package com.ckds.movieapp.screens.search

import androidx.lifecycle.ViewModel
import com.ckds.movieapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository): ViewModel() {


}