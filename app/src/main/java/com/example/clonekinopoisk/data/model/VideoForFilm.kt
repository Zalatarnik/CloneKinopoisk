package com.example.clonekinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class VideoForFilm(
    @SerializedName("url")
    val urlVideo: String,
    @SerializedName("name")
    val nameVideo: String,
    @SerializedName("site")
    val siteVideo: String
)