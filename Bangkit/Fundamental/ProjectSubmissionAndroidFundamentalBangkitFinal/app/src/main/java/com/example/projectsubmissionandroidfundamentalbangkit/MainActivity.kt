package com.example.projectsubmissionandroidfundamentalbangkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.datastore.SettingPreferences
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.datastore.dataStore
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.ActivityMainBinding
import com.example.projectsubmissionandroidfundamentalbangkit.utils.SettingPrefViewModelFactory
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val factory = SettingPrefViewModelFactory(pref)
        viewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]

        setupTheme()

    }

    private fun setupTheme() {
        viewModel.getThemeSettings().observe(this){ isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}