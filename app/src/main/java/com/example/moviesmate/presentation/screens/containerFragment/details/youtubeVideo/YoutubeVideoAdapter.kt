package com.example.moviesmate.presentation.screens.containerFragment.details.youtubeVideo

import com.example.moviesmate.databinding.FragmentYoutubeVideoBinding
import com.example.moviesmate.domain.model.Youtube
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YoutubeVideoAdapter {

    lateinit var onBackClicked: () -> Unit
    lateinit var onVideoReady: (YouTubePlayer) -> Unit

    fun bind(binding: FragmentYoutubeVideoBinding, video: Youtube?) {
        binding.btnBack.setOnClickListener {
            onBackClicked()
        }

        val trailer = video?.results
            ?.firstOrNull { it?.type.equals("Trailer", ignoreCase = true) }
            ?.key

        trailer?.let { trailerKey ->
            binding.youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(trailerKey, 0f)
                    onVideoReady(youTubePlayer)
                }
            })
        }
    }
}