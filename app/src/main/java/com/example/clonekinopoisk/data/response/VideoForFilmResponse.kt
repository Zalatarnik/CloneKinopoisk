package com.example.clonekinopoisk.data.response

import com.example.clonekinopoisk.data.model.VideoForFilm
import com.google.gson.annotations.SerializedName

data class VideoForFilmResponse(
    @SerializedName("items")
    val items: ArrayList<VideoForFilm>
)