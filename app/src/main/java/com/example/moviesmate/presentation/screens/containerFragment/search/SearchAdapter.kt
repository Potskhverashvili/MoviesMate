package com.example.moviesmate.presentation.screens.containerFragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemGenreCategoryRvBinding
import com.example.moviesmate.databinding.ItemSearchedMoviesBinding
import com.example.moviesmate.domain.model.CategoryMovies

class SearchAdapter(
    private val categoryMovies: List<CategoryMovies.Result>,
    private val categoryAdapter: CategoryAdapter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CategoriesViewHolder(private val binding: ItemGenreCategoryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.categoriesRecyclerView.adapter = categoryAdapter
        }
    }

    inner class ItemCategoryMovieHolder(private val binding: ItemSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: CategoryMovies.Result) {
            binding.tvMovieTitle.text = movies.title

            Glide.with(binding.ivMoviePoster)
                .load(movies.poster_path)
                .into(binding.ivMoviePoster)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            VIEW_TYPE_CATEGORIES -> {
                val binding = ItemGenreCategoryRvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CategoriesViewHolder(binding)
            }

            VIEW_TYPE_MOVIE -> {
                val binding =
                    ItemSearchedMoviesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ItemCategoryMovieHolder(binding)
            }

            else -> throw IllegalArgumentException("Wrong viewType was found: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return categoryMovies.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoriesViewHolder -> holder.bind()
            is ItemCategoryMovieHolder -> holder.bind(categoryMovies[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_CATEGORIES
            else -> VIEW_TYPE_MOVIE
        }
    }

    companion object {
        const val VIEW_TYPE_CATEGORIES = 0
        const val VIEW_TYPE_MOVIE = 1
    }
}