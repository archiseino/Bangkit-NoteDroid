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
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentFollowerBinding
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.FollowerViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.progressindicator.CircularProgressIndicator

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel : FollowerViewModel by viewModels()

    private lateinit var adapter : UserResponseAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var loadingIndicator : CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadFollower()

        return binding.root
    }

    private fun loadFollower() {
        viewModel.setUsername(DetailFragment.USER_NAME)
        viewModel.getFollower()

        loadingIndicator = binding.isLoading
        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) loadingIndicator.show() else loadingIndicator.hide()
        }

        viewModel.followerUser.observe(viewLifecycleOwner) {follower ->
            adapter.submitList(follower)
        }

    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvFollower
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divider)

        adapter = UserResponseAdapter()
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}