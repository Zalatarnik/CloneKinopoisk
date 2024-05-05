package com.example.clonekinopoisk.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(): ViewModel() {
    val userId = MutableLiveData<String>()
    fun getUserId(){
        return userId.postValue(Firebase.auth.currentUser?.uid)
    }
}