package com.example.clonekinopoisk.data.response

import com.example.clonekinopoisk.data.model.FilmWithFilmId

data class SearchResponse (
    val films: ArrayList<FilmWithFilmId>
    )