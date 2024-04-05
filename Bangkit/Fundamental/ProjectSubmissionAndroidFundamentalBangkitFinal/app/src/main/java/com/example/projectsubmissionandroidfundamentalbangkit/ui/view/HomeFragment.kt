package com.example.projectsubmissionandroidfundamentalbangkit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsubmissionandroidfundamentalbangkit.R
import com.example.projectsubmissionandroidfundamentalbangkit.adapter.UserResponseAdapter
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.UserResponse
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentHomeBinding
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.HomeViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingIndicator : LinearProgressIndicator
    private lateinit var searchView : SearchView
    private lateinit var searchBar : SearchBar
    private lateinit var imageView: ImageView
    private lateinit var adapter : UserResponseAdapter
    private lateinit var backPressedCallback : OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        searchBarMenuListener()
        setupSearchInput()
        onBackHandler()
        setupRecyclerView()

        return binding.root
    }

    private fun searchBarMenuListener() {
        searchBar = binding.searchBar
        searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_setting ->  findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
                R.id.menu_favorites -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment())
            }
            return@setOnMenuItemClickListener true
        }

    }


    private fun onBackHandler() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (searchView.isShowing) {
                    searchView.hide()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

    }


    private fun setupSearchInput() {
        searchView = binding.searchView
        searchView.setupWithSearchBar(binding.searchBar)
        searchView.editText.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchView.text.toString().trim()
                if (query.isNotEmpty()) {
                    binding.searchBar.setText(binding.searchView.text)
                    binding.searchView.hide()
                    homeViewModel.searchUserResult(query)
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        loadingIndicator = binding.isLoading
        homeViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) loadingIndicator.show() else loadingIndicator.hide()
        }

    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvResult
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divider)

        adapter = UserResponseAdapter()
        recyclerView.adapter = adapter

        adapter.setOnItemCallback(object : UserResponseAdapter.OnItemClickCallback{
            override fun onItemClicked(model: UserResponse) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(model.login!!)
                findNavController().navigate(action)
            }
        })

        recyclerView = binding.rvResult
        imageView = binding.ivNotFound
        homeViewModel.isFound.observe(viewLifecycleOwner) {isFound ->
            if (isFound) {
                recyclerView.visibility = View.VISIBLE
                imageView.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.INVISIBLE
                imageView.visibility = View.VISIBLE
            }
        }

        homeViewModel.usersResult.observe(viewLifecycleOwner) {userList ->
            (adapter).submitList(userList)
        }

    }

    override fun onResume() {
        super.onResume()
        backPressedCallback.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
