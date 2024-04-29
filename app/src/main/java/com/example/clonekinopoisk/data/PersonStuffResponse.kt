package com.example.clonekinopoisk.data

import com.example.clonekinopoisk.data.PersonInStaff
import com.google.gson.annotations.SerializedName

data class PersonStuffListResponse (val people: List<PersonInStaff>){

    //val response = gson.fromJson(jsonString, PersonStuffListResponse::class.java)
   // val people = response.people
}


