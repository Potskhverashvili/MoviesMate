package com.example.moviesmate.presentation.screens.containerFragment.details.youtubeVideo

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesmate.databinding.FragmentYoutubeVideoBinding
import com.example.moviesmate.core.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class YoutubeVideoFragment :
    BaseFragment<FragmentYoutubeVideoBinding>(FragmentYoutubeVideoBinding::inflate) {

    private val viewModel by viewModel<YoutubeVideoViewModel>()
    private val args: YoutubeVideoFragmentArgs by navArgs()
    private var youTubePlayer: YouTubePlayer? = null
    private val youtubeAdapter = YoutubeVideoAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun viewCreated() {
        lifecycle.addObserver(binding.youtubeVideo)
        setListeners()
        setCollectors()
    }

    private fun setListeners() {
        youtubeAdapter.onBackClicked = { navigateBack() }

        youtubeAdapter.onVideoReady = { player ->
            youTubePlayer = player
        }

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
                viewModel.movieTrailer.collect { youtubeVideo ->
                    youtubeAdapter.bind(binding, youtubeVideo)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(binding.youtubeVideo)
    }

}