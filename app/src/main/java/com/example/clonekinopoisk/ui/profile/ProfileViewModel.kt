package com.example.clonekinopoisk.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.domain.SharedPreferencesRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
) : ViewModel() {

    val email = MutableLiveData<String>()
    val photo = MutableLiveData<String>()

        fun getInfoUser(user:FirebaseUser?){
            user?.let {
                email.postValue(it.email.toString())
                photo.postValue(it.photoUrl.toString())
            }
        }
}