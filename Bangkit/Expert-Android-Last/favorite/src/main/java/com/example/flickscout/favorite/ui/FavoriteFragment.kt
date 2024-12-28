package com.example.flickscout.favorite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flickscout.core.ui.MoviesAdapter
import com.example.flickscout.favorite.di.favoriteModule
import com.example.flickscout.favorite.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(favoriteModule)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val movieAdapter = MoviesAdapter { movie ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFavoriteFragment(movie)
            findNavController().navigate(action)
        }
        binding.rvFavorite.adapter = movieAdapter


        viewModel.getFavoriteMovies()
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            movieAdapter.submitList(movie)
            binding.viewEmpty.root.visibility =
                if (movie.isEmpty()) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}