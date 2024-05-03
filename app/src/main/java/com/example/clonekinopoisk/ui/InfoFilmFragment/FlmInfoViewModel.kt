package com.example.clonekinopoisk.ui.InfoFilmFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.data.FilmFullInfo
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.data.FilmsResponse
import com.example.clonekinopoisk.data.VideoForFilm
import com.example.clonekinopoisk.data.VideoForFilmResponse
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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
    val NameFilmOriginal = MutableLiveData<String>()
    val NameFilmRussen = MutableLiveData<String>()
    val rating = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val shortDescription = MutableLiveData<String>()
    val longDescription = MutableLiveData<String>()
    val ageLimits = MutableLiveData<String>()

    val listRelated = MutableLiveData<ArrayList<Film>>()
    val videForFilm = MutableLiveData<ArrayList<VideoForFilm>>()
    // val listGenres = MutableLiveData<ArrayList<String>>()
    // val listPerson = MutableLiveData<List<PersonInStaff>>()


    fun getUrlVideosFromList(videoList: ArrayList<VideoForFilm>):  String? {
        val youtubeVideos = videoList.filterList { siteVideo == "YOUTUBE"}
//        return youtubeVideos.firstOrNull()?.urlVideo
        return videoList.firstOrNull()?.urlVideo
    }

    fun getFilmInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<FilmFullInfo> = repository.getOneFilm(id)
            val idRead = id.toInt()
            val responseRelated: Response<FilmsResponse> = repository.getRelated(id)
//            val responsePerson: Response<PersonStuffListResponse> = repository.getStuff(idRead)
            val responseVideo: Response<VideoForFilmResponse> = repository.getVideo(id)




            if (response.isSuccessful) {
                URLimage.postValue(response.body()?.coverUrl)
                NameFilmOriginal.postValue(response.body()?.nameOriginal)
                NameFilmRussen.postValue(response.body()?.nameRu)
                rating.postValue(response.body()?.rating)
                year.postValue(response.body()?.year)
                ageLimits.postValue(response.body()?.ratingAgeLimits)
                shortDescription.postValue(response.body()?.slogan)

                longDescription.postValue(response.body()?.shortDescription)
                listRelated.postValue(responseRelated.body()?.items)

                ClassForFavourite.postValue(response.body())
                idThis.postValue(response.body()?.id)
                videForFilm.postValue(responseVideo.body()?.items)


//                listGenres.postValue(response.body()?.genres)
//                listPerson.postValue(responsePerson.body()?.people)

            }
        }
    }
}

