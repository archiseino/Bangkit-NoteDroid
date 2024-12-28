package com.example.flickscout.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flickscout.core.ui.MoviesAdapter
import com.example.flickscout.di.favoriteModule
import com.example.flickscout.favorite.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding : FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val movieAdapter = MoviesAdapter { movie ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movie)
            findNavController().navigate(action)
        }
        binding.rvFavorite.adapter = movieAdapter

        loadKoinModules(favoriteModule)

        viewModel.getFavoriteMovies()
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            movieAdapter.submitList(movie)
            binding.viewEmpty.root.visibility =
                if (movie.isEmpty()) View.VISIBLE else View.GONE
        }

        return binding.root
    }
}