package com.example.clonekinopoisk.domain

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.ui.InfoFilmFragment.DEFAULT_URL_IMAGE
import javax.inject.Inject

class ExtensionRepository @Inject constructor() {

    fun ImageView.loadImage(url: String?){
        Glide.with(context)
            .load( (url.takeIf { !it.isNullOrEmpty() } )?: DEFAULT_URL_IMAGE)
            .into(this)
    }
}