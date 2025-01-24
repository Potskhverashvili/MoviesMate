package com.example.moviesmate.presentation.screens.containerFragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesmate.databinding.ItemGenreCategoryBinding
import com.example.moviesmate.domain.model.GenresType

class CategoryAdapter :
    ListAdapter<GenresType.Genre, CategoryAdapter.CategoryViewHolder>(CategoryItemGenreCallBack()) {

    var onGenreClick: (genre: GenresType.Genre) -> Unit = {}
    private var selectedGenreId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(
            ItemGenreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, genre.id == selectedGenreId, onGenreClick)
    }

    fun updateSelectedGenre(selectedId: Int?) {
        val previousSelectedGenreId = selectedGenreId
        selectedGenreId = selectedId

        // Only update the previous and newly selected items to avoid unnecessary changes
        val previousIndex = currentList.indexOfFirst { it.id == previousSelectedGenreId }
        val newIndex = currentList.indexOfFirst { it.id == selectedGenreId }

        notifyItemChangedIfValid(previousIndex)
        notifyItemChangedIfValid(newIndex)
    }

    private fun notifyItemChangedIfValid(index: Int) {
        if (index != -1) {
            notifyItemChanged(index)
        }
    }

    class CategoryViewHolder(private val binding: ItemGenreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenresType.Genre, isSelected: Boolean, onItemClick: (GenresType.Genre) -> Unit) {
            binding.genreTextView.text = genre.name
            binding.root.setOnClickListener { onItemClick(genre) }
            binding.genreCardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (isSelected) android.R.color.holo_orange_light else android.R.color.transparent
                )
            )
        }
    }

    private class CategoryItemGenreCallBack : DiffUtil.ItemCallback<GenresType.Genre>() {
        override fun areItemsTheSame(oldItem: GenresType.Genre, newItem: GenresType.Genre) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GenresType.Genre, newItem: GenresType.Genre) =
            oldItem == newItem
    }
}



