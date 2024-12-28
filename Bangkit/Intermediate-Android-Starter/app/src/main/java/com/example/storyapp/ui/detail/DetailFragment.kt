package com.example.storyapp.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.amulyakhare.textdrawable.TextDrawable
import com.example.storyapp.R
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.stories.Story
import com.example.storyapp.databinding.FragmentDetailBinding
import com.example.storyapp.utils.Colors
import com.example.storyapp.utils.Toaster
import com.example.storyapp.utils.getTextDrawable
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val storyId = args.storyId
        viewModel.getDetailStory(storyId)

        val topAppBar = activity?.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar?.visibility = View.VISIBLE

        setupObserver()

        return binding.root
    }

    private fun setupObserver() {
        viewModel.detailResult.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled().let { detailResult ->
                when (detailResult) {
                    is ApiResponse.Loading -> binding.loadingIndicator.show()
                    is ApiResponse.Error -> {
                        binding.loadingIndicator.hide()
                        Toaster.show(requireContext(), detailResult.error)
                    }
                    is ApiResponse.Success -> {
                        setupUi(detailResult.data.story)
                        Toaster.show(requireContext(), detailResult.data.message)
                        binding.loadingIndicator.hide()
                    }
                    else -> binding.loadingIndicator.hide()
                }

            }
        }
    }

    private fun setupUi(detailResult: Story) {
        with(binding) {
            tvDetailDesc.text = detailResult.description
            tvDetailName.text = detailResult.name
            val textDrawable = getTextDrawable(Colors.getRandomColor(), TextDrawable.SHAPE_ROUND, detailResult.name.first().toString())
            ivAvatar.setImageDrawable(textDrawable)
            ivDetailPhoto.load(detailResult.photoUrl)
        }
    }
}