package com.example.clonekinopoisk.data

import com.google.gson.annotations.SerializedName
import java.time.Year

data class Film(
    @SerializedName("kinopoiskId")
    val id: String,
    @SerializedName("nameOriginal")
    val nameOriginal: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("ratingKinopoisk")
    val rating: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("year")
    val year: String

) {
}