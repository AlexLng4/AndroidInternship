package com.example.applicationinternship.ui.movies.viewpager.Upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationinternship.model.MovieResponse
import com.example.applicationinternship.network.RetrofitInstance
import com.example.applicationinternship.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel:ViewModel (){
    private val dataRepository= DataRepository(RetrofitInstance.getClient())
    val movieLiveData = MutableLiveData<MovieResponse>()


    fun fetchMovieUpcoming(page_number:Int) {
        viewModelScope.launch {
            val movie = withContext(Dispatchers.IO) {
                dataRepository.getMoviesUpcoming(page_number)
            }
            movieLiveData.value = movie
        }
    }
}