package com.example.clonekinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class FilmFullInfo (
    @SerializedName("kinopoiskId")
    val id: String,
    @SerializedName("nameOriginal")
    val nameOriginal: String,
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("ratingKinopoisk")
    val rating: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("genres")
    val genres: ArrayList<Genre>,
    @SerializedName("coverUrl")
    val coverUrl: String,
    @SerializedName("slogan")
    val slogan: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("ratingAgeLimits")
    val ratingAgeLimits: String,

    ){
    constructor() : this(
        id ="",
        nameOriginal = "", nameRu="",
        nameEn ="",
        rating="", posterUrl="", year="",
        genres=arrayListOf(),
        coverUrl="", slogan="",
        shortDescription="",
        ratingAgeLimits="т"
    )
}