package com.example.clonekinopoisk.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.ui.mainFragment.TopFilmsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerView, TopFilmsFragment())
            .commit()
    }
}