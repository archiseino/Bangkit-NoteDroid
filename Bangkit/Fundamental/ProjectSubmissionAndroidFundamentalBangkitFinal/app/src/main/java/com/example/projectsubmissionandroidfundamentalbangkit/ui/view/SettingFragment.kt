package com.example.projectsubmissionandroidfundamentalbangkit.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.datastore.SettingPreferences
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.datastore.dataStore
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.FragmentSettingBinding
import com.example.projectsubmissionandroidfundamentalbangkit.utils.SettingPrefViewModelFactory
import com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel.SettingViewModel
import com.google.android.material.appbar.MaterialToolbar

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var topAppBar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        setupActionBar()
        setupTheme()

        return binding.root
    }

    private fun setupActionBar() {
        topAppBar = binding.topAppBar
        topAppBar.apply {
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    private fun setupTheme() {
         val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val factory = SettingPrefViewModelFactory(pref)
         val viewModel: SettingViewModel by viewModels { factory }

        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }

    }

}