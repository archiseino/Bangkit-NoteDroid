package com.example.flickscout.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.flickscout.R
import com.example.flickscout.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val movie = args.movie
        setStatusFavorite(movie.isFavorite)

        with(binding) {
            ivDetailImage.load("https://image.tmdb.org/t/p/w500"+ movie.posterPath)
            tvDetailDescription.text = movie.overview
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.releaseDate
            tvPopularity.text = String.format(movie.popularity.toString())
            fab.setOnClickListener {
                var statusFavorite = movie.isFavorite
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
        return binding.root
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_unfavorite))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}