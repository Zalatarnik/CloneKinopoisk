package com.example.clonekinopoisk.ui.mainFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.model.Film
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.data.response.FilmsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TopFilmsViewModel @Inject constructor(
    private val repository: FilmsRepository
): ViewModel() {

    val listNewAllTypes = MutableLiveData<ArrayList<Film>> ()
    val listTopPopularFilms = MutableLiveData<ArrayList<Film>> ()
    val listTopPopularShows = MutableLiveData<ArrayList<Film>> ()
    val listClosesReleases = MutableLiveData<ArrayList<Film>> ()
    val listFamilyFilms = MutableLiveData<ArrayList<Film>> ()
    val listLoveTheme = MutableLiveData<ArrayList<Film>> ()
    val listKidsAnimationTheme = MutableLiveData<ArrayList<Film>> ()


    fun loadInfo() {
        viewModelScope.launch(Dispatchers.IO) {

            val responseNewAllTypes: Response<FilmsResponse> = repository.getNewAllType()
            val responseFilms: Response<FilmsResponse> = repository.getTopPopularFilms()
            val responseShows: Response<FilmsResponse> = repository.getTopPopularShow()
            val responseClosesReleases: Response<FilmsResponse> = repository.getClosesReleases()
            val responseFamilyFilms: Response<FilmsResponse> = repository.getFamilyFilms()
            val responseLoveTheme: Response<FilmsResponse> = repository.getLoveTheme()
            val responseKidsAnimationTheme: Response<FilmsResponse> = repository.getKidsAnimationTheme()

            if (responseFilms.isSuccessful) {
                listNewAllTypes.postValue(responseNewAllTypes.body()?.items)
            }
            if(responseFilms.isSuccessful){
                listTopPopularFilms.postValue(responseFilms.body()?.items)
            }
            if(responseShows.isSuccessful){
                listTopPopularShows.postValue(responseShows.body()?.items)
            }
            if(responseClosesReleases.isSuccessful){
                listClosesReleases.postValue(responseClosesReleases.body()?.items)
            }
            if(responseFamilyFilms.isSuccessful){
                listFamilyFilms.postValue(responseFamilyFilms.body()?.items)
            }
            if(responseLoveTheme.isSuccessful){
                listLoveTheme.postValue(responseLoveTheme.body()?.items)
            }
            if(responseKidsAnimationTheme.isSuccessful){
                listKidsAnimationTheme.postValue(responseKidsAnimationTheme.body()?.items)
            }
        }
    }
}


