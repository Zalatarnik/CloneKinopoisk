package com.example.clonekinopoisk.ui.InfoFilmFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.data.FilmFullInfo
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.data.FilmsResponse
import com.example.clonekinopoisk.data.PersonInStaff
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    // val listGenres = MutableLiveData<ArrayList<String>>()
     val listPerson = MutableLiveData<ArrayList<PersonInStaff>>()



    fun getFilmInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<FilmFullInfo> = repository.getOneFilm(id)
            val idRead = id.toInt()
            val responseRelated: Response<FilmsResponse> = repository.getRelated(id)
            val responsePerson: Response<ArrayList<PersonInStaff>> = repository.getStuff(idRead)




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
//                listGenres.postValue(response.body()?.genres)
                listPerson.postValue(responsePerson.body())

            }
        }
    }
}

