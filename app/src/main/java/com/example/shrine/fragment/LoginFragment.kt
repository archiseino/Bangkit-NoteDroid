package com.example.shrine.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shrine.R
import com.example.shrine.databinding.ShrLoginFragmentBinding

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    private lateinit var binding : ShrLoginFragmentBinding
    private lateinit var nextButton : Button
    private lateinit var passwordText : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ShrLoginFragmentBinding.inflate(inflater, container, false)

        setupPassword()

        return binding.root
    }

    private fun setupPassword() {
        // Set an error if the password is less than 8 characters, and clear the error if there's none
        passwordText = binding.passwordEditText
        nextButton = binding.nextButton
        nextButton.setOnClickListener {
            if (!isPasswordValid(passwordText.text)) {
                passwordText.error = getString(R.string.shr_error_password)
            } else {
                passwordText.error = null
                findNavController().navigate(R.id.action_loginFragment_to_productGridFragment)
            }
        }

        passwordText.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(binding.passwordEditText.text)) {
                binding.passwordTextInput.error = null //Clear the error
            }
            false
        }

    }

    /*
        In reality, this will have more complex logic including, but not limited to, actual
        authentication of the username and password.
     */
    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
}
