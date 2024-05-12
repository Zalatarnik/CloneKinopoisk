package com.example.clonekinopoisk.ui.searchFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.model.FilmWithFilmId
import com.example.clonekinopoisk.data.response.SearchResponse
import com.example.clonekinopoisk.domain.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchFilmsViewModel @Inject constructor(
    private val repository: FilmsRepository
) : ViewModel(){
    val listFound = MutableLiveData<ArrayList<FilmWithFilmId>>()

    fun loadInfo(keyword:String,page:Int){
        viewModelScope.launch (Dispatchers.IO) {
            val responseSearch: Response<SearchResponse> = repository.searchByKeyword(keyword,page)

            if(responseSearch.isSuccessful) {
                listFound.postValue(responseSearch.body()?.films)
            }
        }
    }
}