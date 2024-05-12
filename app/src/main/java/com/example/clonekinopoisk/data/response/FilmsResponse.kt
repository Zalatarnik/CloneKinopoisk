package com.example.clonekinopoisk.data.response

import com.example.clonekinopoisk.data.model.Film
import com.google.gson.annotations.SerializedName

data class FilmsResponse (
    @SerializedName("items")
    var items: ArrayList<Film>
)