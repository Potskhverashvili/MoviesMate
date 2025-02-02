package com.example.moviesmate.presentation.screens.containerFragment.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesmate.R
import com.example.moviesmate.databinding.ItemActorBinding
import com.example.moviesmate.domain.model.ActorDetails

class ActorDetailAdapter :
    ListAdapter<ActorDetails.Cast, ActorDetailAdapter.ActorViewHolder>(ActorDiffCallBack()) {

    var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ItemActorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ActorViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: ActorDetails.Cast) = with(binding) {
            actorName.text = actor.original_name

            Glide.with(actorImage.context)
                .load("https://image.tmdb.org/t/p/w500${actor.profile_path}")
                .placeholder(R.color.red)
                .error(R.drawable.ic_launcher_background)
                .into(actorImage)

            binding.root.setOnClickListener {
                actor.id?.let { it1 -> onItemClick.invoke(it1) }
            }

        }
    }

    class ActorDiffCallBack : DiffUtil.ItemCallback<ActorDetails.Cast>() {
        override fun areItemsTheSame(
            oldItem: ActorDetails.Cast,
            newItem: ActorDetails.Cast
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActorDetails.Cast,
            newItem: ActorDetails.Cast
        ): Boolean {
            return oldItem == newItem
        }

    }
}