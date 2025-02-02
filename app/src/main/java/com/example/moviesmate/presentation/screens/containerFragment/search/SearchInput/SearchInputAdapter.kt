package com.example.moviesmate.presentation.screens.containerFragment.search.SearchInput

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemSearchInputBinding
import com.example.moviesmate.domain.model.SearchInput

class SearchInputAdapter :
    ListAdapter<SearchInput.SearchedMovie, SearchInputAdapter.SearchInputViewHolder>(
        SearchMovieCallBack()
    ) {

    var onItemClick: (SearchInput.SearchedMovie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchInputViewHolder {
        return SearchInputViewHolder(
            ItemSearchInputBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchInputViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchInputViewHolder(private val binding: ItemSearchInputBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: SearchInput.SearchedMovie) = with(binding) {
            movieTitle.text = movie.title

            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(moviePoster)
                .load(posterUrl)
                .into(moviePoster)

            binding.root.setOnClickListener {
                onItemClick.invoke(movie)
            }

        }
    }

    class SearchMovieCallBack() : DiffUtil.ItemCallback<SearchInput.SearchedMovie>() {
        override fun areItemsTheSame(
            oldItem: SearchInput.SearchedMovie,
            newItem: SearchInput.SearchedMovie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchInput.SearchedMovie,
            newItem: SearchInput.SearchedMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

}