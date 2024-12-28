package com.dicoding.asclepius.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.NewsAdapter
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.utils.FIRST_CATEGORY_LABEL_EXTRA
import com.dicoding.asclepius.utils.FIRST_CATEGORY_SCORE_EXTRA
import com.dicoding.asclepius.utils.IMAGE_URI_EXTRA
import com.dicoding.asclepius.utils.SECOND_CATEGORY_LABEL_EXTRA
import com.dicoding.asclepius.utils.SECOND_CATEGORY_SCORE_EXTRA
import com.dicoding.asclepius.viewmodel.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var adapter : NewsAdapter
    private lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        setupAppBar()

        val previewImage = intent.getStringExtra(IMAGE_URI_EXTRA)
        val firstLabel = intent.getStringExtra(FIRST_CATEGORY_LABEL_EXTRA)
        val firstScore = intent.getStringExtra(FIRST_CATEGORY_SCORE_EXTRA)
        val secondLabel = intent.getStringExtra(SECOND_CATEGORY_LABEL_EXTRA)
        val secondScore = intent.getStringExtra(SECOND_CATEGORY_SCORE_EXTRA)

        with(binding) {
            resultImage.setImageURI(previewImage?.toUri())
            tvFirstLabel.text = firstLabel
            tvFirstScore.text = firstScore
            tvSecondLabel.text = secondLabel
            tvSecondScore.text = secondScore
            val standardBottomSheetBehaviour = BottomSheetBehavior.from(standardBottomSheet)
            standardBottomSheetBehaviour.peekHeight = 128
            standardBottomSheetBehaviour.isHideable = false
        }

        setupRecyclerView()

        viewModel.getNews()

    }

    private fun setupRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)

        adapter = NewsAdapter()
        binding.rvNews.adapter = adapter

        viewModel.news.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun setupAppBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.elevation = 0f
    }

}