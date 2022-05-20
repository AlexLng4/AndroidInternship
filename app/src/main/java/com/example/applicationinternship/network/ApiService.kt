package com.example.applicationinternship.network

import com.example.applicationinternship.model.Movie
import com.example.applicationinternship.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/top_rated")
    suspend fun getMoviesRated(@Query("page")page:Int):MovieResponse

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(@Query("page")page:Int):MovieResponse

    @GET("movie/popular")
    suspend fun getMoviesPopular(@Query("page")page:Int):MovieResponse

    @GET("movie/upcoming")
    suspend fun getMoviesUpcoming(@Query("page")page:Int):MovieResponse

    @GET("tv/popular")
    suspend fun getTVPopular(@Query("page")page:Int):MovieResponse
    @GET("/3/movie/760868?api_key=43d99e9835a01d2ab7a821edc63c0ec1")
    suspend fun getMovieDetails(): Movie
}