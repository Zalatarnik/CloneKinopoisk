package com.example.clonekinopoisk.data

import com.example.clonekinopoisk.data.model.FilmFullInfo
import com.example.clonekinopoisk.data.model.PersonInStaff
import com.example.clonekinopoisk.data.response.FilmsResponse
import com.example.clonekinopoisk.data.response.SearchResponse
import com.example.clonekinopoisk.data.response.VideoForFilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //New Movies
    @GET("v2.2/films?order=RATING&type=ALL&ratingTo=10&yearFrom=2023&yearTo=3000&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getNewData(): Response<FilmsResponse>


    //Top Movies
    @GET("v2.2/films/collections?type=TOP_POPULAR_MOVIES&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getTopPopularFilmsData(): Response<FilmsResponse>

    //Top Show
    @GET("v2.2/films/collections?type=TOP_250_TV_SHOWS&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getTopPopularShowData(): Response<FilmsResponse>

    //CLOSES_RELEASES
    @GET("v2.2/films/collections?type=CLOSES_RELEASES&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getClosesReleasesData(): Response<FilmsResponse>

    //For the family
    @GET("v2.2/films//collections?type=FAMILY&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getFamilyFilmsData(): Response<FilmsResponse>

    //Love theme
    @GET("v2.2/films/collections?type=LOVE_THEME&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getLoveThemeData(): Response<FilmsResponse>

    //KIDS_ANIMATION
    @GET("v2.2/films/collections?type=KIDS_ANIMATION_THEME&page=1")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getKidsAnimationThemeData(): Response<FilmsResponse>

    //Search by id
    @GET("v2.2/films/{id}")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getOneFilmData(@Path("id") id :String): Response<FilmFullInfo>

     //trailer
    @GET("v2.2/films/{id}/videos")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getVideoData(@Path("id") id :String): Response<VideoForFilmResponse>

    //Stuff
    @GET("v1/staff")
    @Headers("X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7")

    suspend fun getStaffData(@Query ("filmId") id: Int): Response<ArrayList<PersonInStaff>>


    //related movies TV series
    @GET("v2.2/films/{id}/similars")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun getSimilarsData(@Path("id") id :String): Response<FilmsResponse>

    // keyword search
    @GET("v2.1/films/search-by-keyword")
    @Headers(
        "X-API-KEY: 55558c39-b505-4c5f-b6cd-5c91824884b7",
        "Content-Type: application/json")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ):Response<SearchResponse>

}