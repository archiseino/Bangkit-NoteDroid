package com.example.storyapp.ui.profile

import android.app.AlertDialog
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.amulyakhare.textdrawable.TextDrawable
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentProfileBinding
import com.example.storyapp.utils.Colors
import com.example.storyapp.utils.DataStoreManager
import com.example.storyapp.utils.getTextDrawable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.getUserPref()

        setupObserver()

        return binding.root
    }

    private fun setupObserver() {
        viewModel.user.observe(viewLifecycleOwner) {userResult ->
            with(binding) {
                val textDrawable = getTextDrawable(Colors.getRandomColor(), TextDrawable.SHAPE_ROUND, userResult.name.first().toString())
                ivProfileProfile.setImageDrawable(textDrawable)
                tvProfileTitle.text = userResult.name
                actionChangeLanguage.setOnClickListener {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                }
                actionLogout.setOnClickListener {
                    showDialogLogout()
                }
            }
        }
    }

    private fun showDialogLogout() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                dialog.dismiss()
                viewModel.logout()
                findNavController().navigate(R.id.auth)
            }
            .setNegativeButton(R.string.dialog_negative_button, null)
            .create()
        alertDialog.show()
    }

}