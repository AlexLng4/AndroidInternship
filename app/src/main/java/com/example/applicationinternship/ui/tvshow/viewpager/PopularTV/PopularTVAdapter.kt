package com.example.applicationinternship.ui.tvshow.viewpager.PopularTV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applicationinternship.R
import com.example.applicationinternship.model.Movie
import com.example.applicationinternship.network.RetrofitInstance
import com.example.applicationinternship.ui.movies.viewpager.Popular.PopularAdapter
import kotlinx.android.synthetic.main.card_view.view.*

class PopularTVAdapter(private val movies: List<Movie>):
    RecyclerView.Adapter<PopularTVAdapter.PopularTVViewHolder>() {

    var onItemClick : ((Movie) -> Unit) ?= null

    class PopularTVViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bindMovie(movie : Movie){
            val title=movie.title +"-"+movie.releaseDate.substringBefore("-")
            itemView.movie_title.text = title
            itemView.movie_description.text=movie.overview
            Glide.with(itemView).load(RetrofitInstance.POSTER_BASE_URL + movie.poster).into(itemView.movie_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTVViewHolder {
        return PopularTVViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: PopularTVViewHolder, position: Int) {
        val movie =movies[position]
        holder.bindMovie(movies[position])
        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int = movies.size
}