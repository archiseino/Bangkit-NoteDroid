package com.example.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.data.auth.RequestLogin
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.utils.Toaster
import com.google.android.material.animation.AnimatorSetCompat.playTogether
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.start

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setNegativeButton(R.string.dialog_negative_button, null)
                .create()
            alertDialog.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()
        playAnimation()

        return binding.root
    }

    private fun playAnimation() {
        binding.apply {
            val title = ObjectAnimator.ofFloat(tvTitle, View.ALPHA, 1f).setDuration(100)
            val email = ObjectAnimator.ofFloat(evEmailAddress, View.ALPHA, 1f).setDuration(100)
            val password = ObjectAnimator.ofFloat(evPassword, View.ALPHA, 1f).setDuration(100)
            val login = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(100)
            val container = ObjectAnimator.ofFloat(actionContainer, View.ALPHA, 1f).setDuration(100)

            AnimatorSet().apply {
                playSequentially(title, email, password, login, container)
                start()
            }

        }
    }

    private fun setupObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ApiResponse.Loading -> binding.loadingIndicator.show()
                is ApiResponse.Error -> {
                    binding.loadingIndicator.hide()
                    Toaster.show(requireContext(), resultState.error)
                }

                is ApiResponse.Success -> {
                    binding.loadingIndicator.hide()
                    Toaster.show(requireContext(), resultState.data.message)
                    findNavController().navigate(
                        R.id.home,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true)
                            .build()
                    )
                }

                else -> binding.loadingIndicator.hide()
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = evEmailAddress.text?.trim().toString()
                val password = evPassword.text?.trim().toString()
                viewModel.login(RequestLogin(email, password))
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }
}