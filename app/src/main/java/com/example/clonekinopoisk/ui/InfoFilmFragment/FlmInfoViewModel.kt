package com.example.clonekinopoisk.ui.InfoFilmFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.model.Film
import com.example.clonekinopoisk.data.model.FilmFullInfo
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.data.response.FilmsResponse
import com.example.clonekinopoisk.data.model.Genre
import com.example.clonekinopoisk.data.model.PersonInStaff
import com.example.clonekinopoisk.data.model.VideoForFilm
import com.example.clonekinopoisk.data.response.VideoForFilmResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class FlmInfoViewModel @Inject constructor(
    private val repository: FilmsRepository
): ViewModel() {

    val ClassForFavourite = MutableLiveData<FilmFullInfo>()
    val idThis = MutableLiveData<String>()

    val URLimage = MutableLiveData<String>()
    val UrlPoster = MutableLiveData<String>()

    val NameFilmOriginal = MutableLiveData<String>()
    val NameFilmEnglish = MutableLiveData<String>()
    val NameFilmRussen = MutableLiveData<String>()

    val rating = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val longDescription = MutableLiveData<String>()
    val ageLimits = MutableLiveData<String>()

    val listRelated = MutableLiveData<ArrayList<Film>>()
    val videForFilm = MutableLiveData<ArrayList<VideoForFilm>>()
    val listGenres = MutableLiveData<ArrayList<Genre>>()
    val listPerson = MutableLiveData<ArrayList<PersonInStaff>>()


    fun getUrlVideosFromList(videoList: ArrayList<VideoForFilm>):  String? {
        val youtubeVideos = videoList.filterList  { siteVideo == "YOUTUBE" || siteVideo == "YANDEX_DISK"}
        return youtubeVideos.firstOrNull()?.urlVideo
    }

    fun getFilmInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<FilmFullInfo> = repository.getOneFilm(id)
            val idRead = id.toInt()
            val responseRelated: Response<FilmsResponse> = repository.getRelated(id)
            val responsePerson: Response<ArrayList<PersonInStaff>> = repository.getStuff(idRead)
            val responseVideo: Response<VideoForFilmResponse> = repository.getVideo(id)

            if (response.isSuccessful) {
                URLimage.postValue(response.body()?.coverUrl)
                UrlPoster.postValue(response.body()?.posterUrl)

                NameFilmOriginal.postValue(response.body()?.nameOriginal)
                NameFilmEnglish.postValue(response.body()?.nameEn)
                NameFilmRussen.postValue(response.body()?.nameRu)

                rating.postValue(response.body()?.rating)
                year.postValue(response.body()?.year)
                ageLimits.postValue(response.body()?.ratingAgeLimits)
                shortDescription.postValue(response.body()?.slogan)

                longDescription.postValue(response.body()?.shortDescription)

                ClassForFavourite.postValue(response.body())
                idThis.postValue(response.body()?.id)
                listGenres.postValue(response.body()?.genres)
            }

            if(responsePerson.isSuccessful){
                listPerson.postValue(responsePerson.body())
            }

            if(responseRelated.isSuccessful) {
                listRelated.postValue(responseRelated.body()?.items)
            }

            if(responseVideo.isSuccessful){
                videForFilm.postValue(responseVideo.body()?.items)
            }
        }
    }

    fun changingGenreString(text: String): String{
        val regex = Regex("""(\w+)""")
        val genres = regex.findAll(text)
            .map { it.groupValues[1] }
            .joinToString(", ")
        val outputString = genres.replace(Regex("Genre, genre, "), "")
        return outputString
    }
}

