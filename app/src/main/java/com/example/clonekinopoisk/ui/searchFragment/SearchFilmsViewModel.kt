package com.example.clonekinopoisk.ui.searchFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.data.SearchResponse
import com.example.clonekinopoisk.domain.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SearchFilmsViewModel @Inject constructor(
    private val repository: FilmsRepository
) : ViewModel(){

    val listFound = MutableLiveData<ArrayList<Film>?>()

    fun urlEncodeString(str:String): String{
        return URLEncoder.encode(str,"UTF-8")
    }

    fun loadInfo(keyword:String,page:Int){
        viewModelScope.launch (Dispatchers.IO) {
            val responseSearch: Response<SearchResponse> = repository.searchByKeyword(keyword,page)

            if(responseSearch.isSuccessful){
                listFound.postValue(responseSearch.body()?.films)
            }else{
                listFound.postValue(null)
            }
        }
    }



}