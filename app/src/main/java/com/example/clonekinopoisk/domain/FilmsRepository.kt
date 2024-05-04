package com.example.clonekinopoisk.domain

import com.example.clonekinopoisk.data.Api
import javax.inject.Inject

class FilmsRepository @Inject constructor(private val api: Api) {

    suspend fun getNewAllType() = api.getNewData()
    suspend fun getTopPopularFilms()= api.getTopPopularFilmsData()
    suspend fun getTopPopularShow()= api.getTopPopularShowData()
    suspend fun getClosesReleases()= api.getClosesReleasesData()
    suspend fun getFamilyFilms()= api.getFamilyFilmsData()
    suspend fun getLoveTheme()= api.getLoveThemeData()
    suspend fun getKidsAnimationTheme()= api.getKidsAnimationThemeData()


    suspend fun getOneFilm(id:String)= api.getOneFilmData(id)
    suspend fun getRelated(id: String) = api.getSimilarsData(id)
    suspend fun getStuff(id:Int) = api.getStaffData(id)
//    suspend fun getStuff(id:Int) = api.ngetStaffData(id)
    suspend fun getVideo(id:String) = api.getVideoData(id)

    suspend fun searchByKeyword(keyword:String,page:Int)= api.searchByKeyword(keyword,page)



}