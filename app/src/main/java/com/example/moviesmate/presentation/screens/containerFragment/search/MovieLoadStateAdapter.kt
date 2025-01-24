package com.example.moviesmate.presentation.screens.containerFragment.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesmate.databinding.ItemFooterLoaderBinding

class MovieLoadStateAdapter : LoadStateAdapter<MovieLoadStateAdapter.MovieLoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        Log.d("MovieLoadStateAdapter", "onCreateViewHolder: loadState=$loadState")
        return MovieLoadStateViewHolder(
            ItemFooterLoaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    class MovieLoadStateViewHolder(
        val binding: ItemFooterLoaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {}

}