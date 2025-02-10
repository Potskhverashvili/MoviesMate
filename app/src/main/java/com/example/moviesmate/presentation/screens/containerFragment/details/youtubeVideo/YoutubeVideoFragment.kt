package com.example.moviesmate.presentation.screens.containerFragment.details.youtubeVideo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesmate.databinding.FragmentYoutubeVideoBinding
import com.example.moviesmate.core.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class YoutubeVideoFragment :
    BaseFragment<FragmentYoutubeVideoBinding>(FragmentYoutubeVideoBinding::inflate) {
    private val viewModel by viewModel<YoutubeVideoViewModel>()
    private val args: YoutubeVideoFragmentArgs by navArgs()
    private var youTubePlayer: YouTubePlayer? = null

    override fun viewCreated() {
        lifecycle.addObserver(binding.youtubeVideo)
        setListeners()
        setCollectors()
    }

    private fun setListeners() {
        viewModel.fetchMovieTrailer(args.movieId.toString())

        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

    private fun setCollectors() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieTrailer.collect { video ->
                    val trailer = video?.results
                        ?.filter { it?.type.equals("Trailer", ignoreCase = true) }
                        ?.firstOrNull { it?.site.equals("YouTube", ignoreCase = true) }
                        ?.key
                    binding.youtubeVideo.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            if (trailer != null) {
                                youTubePlayer.cueVideo(trailer, 0f)
                            }
                        }
                    })
                    this@YoutubeVideoFragment.youTubePlayer = youTubePlayer
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(binding.youtubeVideo)
    }

}