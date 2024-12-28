package com.example.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amulyakhare.textdrawable.TextDrawable
import com.example.storyapp.R
import com.example.storyapp.data.auth.RequestRegister
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.databinding.FragmentRegisterBinding
import com.example.storyapp.utils.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupUi()
        setupObserver()
        playAnimation()

        return binding.root

    }

    private fun playAnimation() {
        binding.apply {
            val title = ObjectAnimator.ofFloat(tvTitle, View.ALPHA, 1f).setDuration(100)
            val username = ObjectAnimator.ofFloat(evUsername, View.ALPHA, 1f).setDuration(100)
            val email = ObjectAnimator.ofFloat(evEmailAddress, View.ALPHA, 1f).setDuration(100)
            val password = ObjectAnimator.ofFloat(evPassword, View.ALPHA, 1f).setDuration(100)
            val register = ObjectAnimator.ofFloat(btnRegister, View.ALPHA, 1f).setDuration(100)
            val container = ObjectAnimator.ofFloat(actionContainer, View.ALPHA, 1f).setDuration(100)

            AnimatorSet().apply {
                playSequentially(title, username, email, password, register, container)
                start()
            }

        }
    }

    private fun setupObserver() {
        viewModel.registerStatus.observe(viewLifecycleOwner) {resultState ->
            when(resultState) {
                is ApiResponse.Loading -> binding.loadingIndicator.show()
                is ApiResponse.Error -> {
                    binding.loadingIndicator.hide()
                    Toaster.show(requireContext(), resultState.error)
            }
                is ApiResponse.Success -> {
                    binding.loadingIndicator.hide()
                    Toaster.show(requireContext(), resultState.data.message)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else -> binding
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            btnRegister.setOnClickListener {
                val username = evUsername.text?.trim().toString()
                if (username.isEmpty()) {
                    evUsername.error = getString(R.string.error_username)
                }

                val email = evEmailAddress.text?.trim().toString()
                val password = evPassword.text?.trim().toString()
                viewModel.register(RequestRegister(username, email, password))

            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

        }

    }

}