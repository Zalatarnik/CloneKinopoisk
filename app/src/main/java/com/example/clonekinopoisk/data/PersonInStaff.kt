package com.example.clonekinopoisk.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class PersonInStaff (
    @SerializedName("staffId")
    val staffId: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("professionText")
    val professionText: String
){

}
