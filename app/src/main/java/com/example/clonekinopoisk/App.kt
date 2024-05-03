package com.example.clonekinopoisk

import android.app.Application
import com.example.clonekinopoisk.domain.SharedPreferencesRepository
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()  {
    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
    //        SharedPreferencesRepository.init(applicationContext)
    }
}