package com.example.projectsubmissionandroidfundamentalbangkit.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.projectsubmissionandroidfundamentalbangkit.R
import com.example.projectsubmissionandroidfundamentalbangkit.adapter.ViewStateAdapter
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentDetailBinding
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.DetailViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    companion object {
        lateinit var USER_NAME: String
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var loadingIndicator: LinearProgressIndicator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        setupViewPager()
        loadUserDetail()

        return binding.root
    }


    private fun setupViewPager() {
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPager.adapter = ViewStateAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text =
                    if (position == 0) getText(R.string.tab_followers) else getText(R.string.tab_following)
        }.attach()
    }

    private fun loadUserDetail() {
        USER_NAME = args.username
        detailViewModel.setUsername(USER_NAME)
        detailViewModel.detailProfile()

        topAppBar = binding.topAppBar
        topAppBar.apply {
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            title = USER_NAME
        }

        loadingIndicator = binding.layoutHeader.isLoading
        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) loadingIndicator.show() else loadingIndicator.hide()
        }

        detailViewModel.user.observe(viewLifecycleOwner) { user ->
            with(binding) {
                layoutHeader.tvName.text = user?.name
                layoutHeader.tvUsername.text = user?.login
                layoutHeader.tvFollower.text = user?.followers.toString()
                layoutHeader.tvFollowing.text = user?.following.toString()

                Glide.with(binding.root.context)
                    .load(user?.avatarUrl)
                    .into(layoutHeader.ivProfilePic)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}