package com.example.clonekinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class FilmWithFilmId(
    //////
    @SerializedName("filmId")
    val id: String,
    //////
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
)
