package com.example.moviesmate.presentation.screens.containerFragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesmate.databinding.ItemGenreCategoryBinding
import com.example.moviesmate.domain.model.GenresType

class CategoryAdapter :
    ListAdapter<GenresType.Genre, CategoryAdapter.CategoryViewHolder>(CategoryItemGenreCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CategoryViewHolder(
        ItemGenreCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(private val binding: ItemGenreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: GenresType.Genre) {
            binding.genreTextView.text = category.name
        }
    }

    private class CategoryItemGenreCallBack() : DiffUtil.ItemCallback<GenresType.Genre>() {
        override fun areItemsTheSame(
            oldItem: GenresType.Genre,
            newItem: GenresType.Genre
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenresType.Genre,
            newItem: GenresType.Genre
        ): Boolean {
            return oldItem == newItem
        }
    }
}

