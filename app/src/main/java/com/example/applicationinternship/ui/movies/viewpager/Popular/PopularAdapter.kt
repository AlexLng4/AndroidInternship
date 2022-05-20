package com.example.applicationinternship.ui.movies.viewpager.Popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applicationinternship.R
import com.example.applicationinternship.model.Movie
import com.example.applicationinternship.network.RetrofitInstance
import kotlinx.android.synthetic.main.card_view.view.*

class PopularAdapter(private val movies: List<Movie>):
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    class PopularViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bindMovie(movie : Movie){
            val title=movie.title +"-"+movie.releaseDate.substringBefore("-")
            itemView.movie_title.text = title
            itemView.movie_description.text=movie.overview
            Glide.with(itemView).load(RetrofitInstance.POSTER_BASE_URL + movie.poster).into(itemView.movie_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
    }

        override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}