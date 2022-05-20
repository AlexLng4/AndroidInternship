package com.example.applicationinternship.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val poster : String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val title:String,
    val overview: String
)