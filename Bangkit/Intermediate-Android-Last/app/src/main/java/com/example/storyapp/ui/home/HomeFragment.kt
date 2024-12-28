package com.example.storyapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.example.storyapp.R
import com.example.storyapp.data.stories.ListStoryItem
import com.example.storyapp.databinding.FragmentHomeBinding
import com.example.storyapp.utils.Colors
import com.example.storyapp.utils.MarginItemDecorator
import com.example.storyapp.utils.getTextDrawable
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : HomeAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()

        return binding.root
    }


    private fun setupUi() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(MarginItemDecorator(resources.getDimensionPixelSize(R.dimen.rv_margin)))

        adapter = HomeAdapter()
        adapter.setOnItemCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(model: ListStoryItem) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(model.id)
                findNavController().navigate(action)
            }
        })

        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
    }

    private fun setupObserver() {
        viewModel.getAllStories()
        viewModel.storiesResult.observe(viewLifecycleOwner) {story ->
            adapter.submitData(lifecycle, story)
        }

        viewModel.getUserPref()
        viewModel.user.observe(viewLifecycleOwner) {user ->
            if (user.token.isEmpty()) {
                findNavController().navigate(R.id.auth)
            } else {
                binding.tvTitle.text = getString(R.string.hello_username, user.name)

                val navigationView = activity?.findViewById<NavigationView>(R.id.nav_view)
                val navHeader = navigationView?.getHeaderView(0)
                navHeader?.apply {
                    findViewById<TextView>(R.id.tvHeadertitle)?.text = user.name
                    val textDrawable = getTextDrawable(Colors.getRandomColor(), TextDrawable.SHAPE_ROUND, user.name.first().toString().uppercase())
                    findViewById<ImageView>(R.id.ivProfileNavigation)?.setImageDrawable(textDrawable)
                }

            }
        }
    }
}