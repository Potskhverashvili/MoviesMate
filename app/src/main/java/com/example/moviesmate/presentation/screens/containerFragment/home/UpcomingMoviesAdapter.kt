package com.example.moviesmate.presentation.screens.containerFragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemUpcomingMoviesBinding
import com.example.moviesmate.domain.model.HomePageMovies

class UpcomingMoviesAdapter :
    ListAdapter<HomePageMovies.Movie, UpcomingMoviesAdapter.UpcomingMoviesViewHolder>(
        DiffUtilCallBack()
    ) {

    var onItemClick: (HomePageMovies.Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        return UpcomingMoviesViewHolder(
            ItemUpcomingMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UpcomingMoviesViewHolder(private val binding: ItemUpcomingMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: HomePageMovies.Movie) {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"
            Glide.with(binding.upcomingMoviePoster)
                .load(imageUrl)
                .into(binding.upcomingMoviePoster)

            binding.upcomingMovieTitle.text = movie.title

            binding.root.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<HomePageMovies.Movie>() {
        override fun areItemsTheSame(
            oldItem: HomePageMovies.Movie,
            newItem: HomePageMovies.Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomePageMovies.Movie,
            newItem: HomePageMovies.Movie
        ): Boolean {
            return oldItem == newItem
        }
    }
}