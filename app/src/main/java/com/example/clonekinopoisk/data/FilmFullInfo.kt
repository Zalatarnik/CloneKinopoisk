package com.example.clonekinopoisk.data

import com.google.gson.annotations.SerializedName

data class FilmFullInfo (
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
    val year: String,

    @SerializedName("coverUrl")
    val coverUrl: String,
    @SerializedName("slogan")
    val slogan: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("ratingAgeLimits")
    val ratingAgeLimits: String,
//    @SerializedName("genres")
//    val genres: ArrayList<String>
)