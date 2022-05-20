package com.example.applicationinternship.repository

import com.example.applicationinternship.model.Movie
import com.example.applicationinternship.model.MovieResponse
import com.example.applicationinternship.network.ApiService


class DataRepository(private val moviesApi: ApiService)
{
    suspend fun getMoviesRated(page_number:Int): MovieResponse {
        return moviesApi.getMoviesRated(page_number)
    }
    suspend fun getMoviesNowPlaying(page_number:Int): MovieResponse {
        return moviesApi.getMoviesNowPlaying(page_number)
    }
    suspend fun getMoviesPopular(page_number:Int): MovieResponse {
        return moviesApi.getMoviesPopular(page_number)
    }
    suspend fun getMoviesUpcoming(page_number:Int): MovieResponse {
        return moviesApi.getMoviesUpcoming(page_number)
    }
    suspend fun getTVPopular(page_number:Int): MovieResponse {
        return moviesApi.getTVPopular(page_number)
    }
}