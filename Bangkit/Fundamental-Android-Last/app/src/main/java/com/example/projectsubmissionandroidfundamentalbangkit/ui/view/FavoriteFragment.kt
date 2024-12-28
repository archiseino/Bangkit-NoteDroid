package com.example.projectsubmissionandroidfundamentalbangkit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsubmissionandroidfundamentalbangkit.adapter.UserEntityAdapter
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentFavoriteBinding
import com.example.projectsubmissionandroidfundamentalbangkit.utils.ViewModelFactory
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.FavoriteViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.divider.MaterialDividerItemDecoration

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var adapter: UserEntityAdapter
    private lateinit var recyclerView : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity().applicationContext)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setupActionBar()
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvFavorites
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(divider)

        adapter = UserEntityAdapter()
        adapter.setOnItemCallback(object: UserEntityAdapter.OnItemClickCallback{
            override fun onItemClicked(model: UserEntity) {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(model.login!!)
                findNavController().navigate(action)
            }
        })
        recyclerView.adapter = adapter

        viewModel.getFavorites().observe(viewLifecycleOwner) { user ->
            adapter.submitList(user)
        }

    }

    private fun setupActionBar() {
        topAppBar = binding.topAppBar
        topAppBar.apply {
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}