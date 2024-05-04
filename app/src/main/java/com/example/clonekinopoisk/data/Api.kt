package com.example.clonekinopoisk.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //Новые
    @GET("v2.2/films?order=RATING&type=ALL&ratingTo=10&yearFrom=2023&yearTo=3000&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getNewData(): Response<FilmsResponse>


    //Топ фильмов
    @GET("v2.2/films/collections?type=TOP_POPULAR_MOVIES&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getTopPopularFilmsData(): Response<FilmsResponse>

    //Топ шоу
    @GET("v2.2/films/collections?type=TOP_250_TV_SHOWS&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getTopPopularShowData(): Response<FilmsResponse>

    //Закрывает выпуски
    @GET("v2.2/films/collections?type=CLOSES_RELEASES&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getClosesReleasesData(): Response<FilmsResponse>

    //Для семьи
    @GET("v2.2/films//collections?type=FAMILY&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getFamilyFilmsData(): Response<FilmsResponse>

    //Любовная тема
    @GET("v2.2/films/collections?type=LOVE_THEME&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getLoveThemeData(): Response<FilmsResponse>

    //Детские мультики
    @GET("v2.2/films/collections?type=KIDS_ANIMATION_THEME&page=1")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getKidsAnimationThemeData(): Response<FilmsResponse>

    //Поиск по id
    @GET("v2.2/films/{id}")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getOneFilmData(@Path("id") id :String): Response<FilmFullInfo>

    // трейлер
 //   @GET("films/{id}/videos")
   // @Headers(
     //   "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
       // "Content-Type: application/json")
    //suspend fun getVideoData(@Path("id") id :String): Response<FilmFullInfo>

    //Персонал, коллектив
    @GET("v1/staff")
    @Headers("X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9")

    suspend fun getStaffData(@Query ("filmId") id: Int): Response<ArrayList<PersonInStaff>>


    //похожие фильмы сериалы
    @GET("v2.2/films/{id}/similars")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun getSimilarsData(@Path("id") id :String): Response<FilmsResponse>

    // поиск по ключевому слову
    @GET("v2.1/films/search-by-keyword")
    @Headers(
        "X-API-KEY: 94465803-dd38-480b-a973-9f3b0062aec9",
        "Content-Type: application/json")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ):Response<SearchResponse>

}