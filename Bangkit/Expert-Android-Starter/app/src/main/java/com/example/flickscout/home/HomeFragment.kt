package com.example.flickscout.home

import com.example.flickscout.home.HomeViewModel
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flickscout.R
import com.example.flickscout.core.data.Resource
import com.example.flickscout.core.ui.MoviesAdapter
import com.example.flickscout.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val movieAdapter = MoviesAdapter { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
            findNavController().navigate(action)
        }
        binding.rvMovies.adapter = movieAdapter

        viewModel.getAllMovies()
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    movieAdapter.submitList(movie.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewError.root.visibility = View.VISIBLE
                    binding.viewError.tvError.text =
                        movie.message ?: getString(R.string.something_wrong)
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }

}