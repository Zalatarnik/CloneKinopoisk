package com.example.clonekinopoisk.ui.profile

import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val repository: FilmsRepository
) : ViewModel() {

}