package com.example.clonekinopoisk.data

import com.google.gson.annotations.SerializedName

data class VideoForFilmResponse(
    @SerializedName("items")
    val items: ArrayList<VideoForFilm>
) {
}