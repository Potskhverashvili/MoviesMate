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

    var onItemClick: (genre: GenresType.Genre) -> Unit = {}
    private var selectedCategoryId: Int? = null
    var onItemLongClick: (genre: GenresType.Genre) -> Unit = {}

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

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, item.id == selectedCategoryId, onItemClick)
    }

    class CategoryViewHolder(private val binding: ItemGenreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            category: GenresType.Genre,
            isSelected: Boolean,
            onItemClick: (genre: GenresType.Genre) -> Unit
        ) {
            binding.genreTextView.text = category.name

            binding.root.setOnClickListener {
                onItemClick(category)
            }


            if (isSelected) {
                binding.genreCardView.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_orange_light)
                )
            } else {
                binding.genreCardView.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, android.R.color.transparent)
                )
            }
        }
    }

    fun setSelectedCategory(selectedCategoryId: Int?) {
        this.selectedCategoryId = selectedCategoryId
        notifyDataSetChanged()
//        submitList(currentList.toList()) // Trigger rebind with updated selected state
    }

    private class CategoryItemGenreCallBack : DiffUtil.ItemCallback<GenresType.Genre>() {
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


