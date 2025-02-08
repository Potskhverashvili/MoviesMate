package com.example.moviesmate.presentation.screens.containerFragment.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemFavoritesBinding
import com.example.moviesmate.domain.model.Movie

class FavoritesAdapter : ListAdapter<Movie, FavoritesAdapter.FavoritesViewHolder>(ItemCallBack()) {

    var onItemDeleteClick: (movie: Movie) -> Unit = {}

    var onItemClick: (movie: Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoritesViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            movieTitle.text = movie.title

            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.image}"
            Glide.with(moviePoster)
                .load(posterUrl)
                .into(moviePoster)

            binding.btnDelete.setOnClickListener {
                onItemDeleteClick.invoke(movie)
            }

            binding.moviePoster.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }

    class ItemCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}