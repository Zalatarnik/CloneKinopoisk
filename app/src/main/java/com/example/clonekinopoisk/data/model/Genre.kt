package com.example.clonekinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("genre")
    val genre: String
) {
    constructor() : this("")
}