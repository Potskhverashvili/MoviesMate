package com.example.moviesmate.presentation.screens.containerFragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemSearchedMoviesBinding
import com.example.moviesmate.domain.model.CategoryMovies

class SearchAdapter :
    PagingDataAdapter<CategoryMovies.Result, SearchAdapter.ItemCategoryMovieHolder>(
        ItemCategoryMovieCallback()
    ) {

    var onItemClick: (CategoryMovies.Result) -> Unit = {}
    var onFavoriteClick: (CategoryMovies.Result) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryMovieHolder {
        return ItemCategoryMovieHolder(
            ItemSearchedMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemCategoryMovieHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class ItemCategoryMovieHolder(private val binding: ItemSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: CategoryMovies.Result) {
            binding.tvMovieTitle.text = movies.title

            val posterUrl = "https://image.tmdb.org/t/p/w500${movies.poster_path}"
            Glide.with(binding.ivMoviePoster)
                .load(posterUrl)
                .into(binding.ivMoviePoster)

            binding.root.setOnClickListener {
                onItemClick.invoke(movies)
            }

            binding.saveToFavorites.setOnClickListener {
                onFavoriteClick.invoke(movies)
            }
        }
    }

    class ItemCategoryMovieCallback : DiffUtil.ItemCallback<CategoryMovies.Result>() {
        override fun areItemsTheSame(
            oldItem: CategoryMovies.Result,
            newItem: CategoryMovies.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryMovies.Result,
            newItem: CategoryMovies.Result
        ): Boolean {
            return oldItem == newItem
        }
    }

}