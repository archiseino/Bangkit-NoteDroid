package com.example.projectsubmissionandroidfundamentalbangkit.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsubmissionandroidfundamentalbangkit.adapter.UserResponseAdapter
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentFollowingBinding
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.FollowingViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.progressindicator.CircularProgressIndicator

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel : FollowingViewModel by viewModels()

    private lateinit var adapter : UserResponseAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var loadingIndicator : CircularProgressIndicator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadFollowing()

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvFollowing
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvFollowing.addItemDecoration(divider)

        adapter = UserResponseAdapter()
        recyclerView.adapter = adapter
    }

    private fun loadFollowing() {
        viewModel.setUsername(DetailFragment.USER_NAME)
        viewModel.getFollowing()

        loadingIndicator = binding.isLoading
        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) loadingIndicator.show() else loadingIndicator.hide()
        }

        viewModel.followingUser.observe(viewLifecycleOwner) {following ->
            adapter.submitList(following)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}