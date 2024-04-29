package com.example.clonekinopoisk.ui.mainFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.data.FilmsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TopFilmsViewModel @Inject constructor(
    private val repository: FilmsRepository
): ViewModel() {

    val nameFilm = MutableLiveData<String>()
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

            if (responseFilms.isSuccessful && responseShows.isSuccessful ) {
                listNewAllTypes.postValue(responseNewAllTypes.body()?.items)
                listTopPopularFilms.postValue(responseFilms.body()?.items)
                listTopPopularShows.postValue(responseShows.body()?.items)

                listClosesReleases.postValue(responseClosesReleases.body()?.items)
                listFamilyFilms.postValue(responseFamilyFilms.body()?.items)
                listLoveTheme.postValue(responseLoveTheme.body()?.items)
                listKidsAnimationTheme.postValue(responseKidsAnimationTheme.body()?.items)
            }else{
//                listTopPopularFilms.postValue(null)
            }
        }
    }
}


