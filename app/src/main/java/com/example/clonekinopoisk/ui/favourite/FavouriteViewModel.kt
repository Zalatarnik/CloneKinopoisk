package com.example.clonekinopoisk.ui.favourite

import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FilmsRepository
) : ViewModel() {

}