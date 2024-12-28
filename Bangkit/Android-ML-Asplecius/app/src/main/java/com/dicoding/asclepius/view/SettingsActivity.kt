package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.datastore.SettingPreferences
import com.dicoding.asclepius.data.local.datastore.datastore
import com.dicoding.asclepius.databinding.ActivitySettingsBinding
import com.dicoding.asclepius.utils.SettingPrefViewModelFactory
import com.dicoding.asclepius.viewmodel.SettingViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var viewModel : SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()

        val preferences = SettingPreferences.getInstance(application.datastore)
        val factory = SettingPrefViewModelFactory(preferences)
        viewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]


        viewModel.getThemeSettings().observe(this) {isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }

        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setThemeSettings(isChecked)
        }

    }

    private fun setupAppBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.elevation = 0f
    }

}