package com.example.clonekinopoisk.data.response

import com.example.clonekinopoisk.data.model.FilmWithFilmId
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("films")
    val films: ArrayList<FilmWithFilmId>
    )