package com.example.moviesmate.presentation.screens.containerFragment.actorBiography

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.databinding.ItemActorFilmographyBinding
import com.example.moviesmate.domain.model.ActorFilmography

class ActorFilmographyAdapter :
    ListAdapter<ActorFilmography.Cast, ActorFilmographyAdapter.ActorFilmographyViewHolder>(
        ItemFilmographyDiffCallBack()
    ) {

    var onItemClick: (movieId: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorFilmographyViewHolder {
        return ActorFilmographyViewHolder(
            ItemActorFilmographyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorFilmographyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ActorFilmographyViewHolder(private val binding: ItemActorFilmographyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(relatedMovie: ActorFilmography.Cast) = with(binding) {
            val posterUrl = "https://image.tmdb.org/t/p/w500${relatedMovie.poster_path}"
            Glide.with(moviePosterImg)
                .load(posterUrl)
                .into(moviePosterImg)

            root.setOnClickListener {
                relatedMovie.id?.let { it1 -> onItemClick.invoke(it1) }
            }
        }
    }

    class ItemFilmographyDiffCallBack :
        DiffUtil.ItemCallback<ActorFilmography.Cast>() {
        override fun areItemsTheSame(
            oldItem: ActorFilmography.Cast,
            newItem: ActorFilmography.Cast
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActorFilmography.Cast,
            newItem: ActorFilmography.Cast
        ): Boolean {
            return oldItem == newItem
        }

    }
}