package com.example.sign_in.presentation.sign_in

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sign_in.R
import com.example.sign_in.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()
    private lateinit var binding: FragmentSignInBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)


        setupListeners()
    }

    private fun setupListeners() {
        binding.signInButton.setOnClickListener {
            if (binding.loginEditText.text.isBlank())
                binding.loginEditText.error = "NOT EMPTY"
            else if (binding.passwordEditText.text.isBlank())
                binding.passwordEditText.error = "NOT EMPTY"
            else {
                try {
                    binding.errorTV.visibility = View.GONE
                    viewModel.signIn(
                        binding.loginEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                } catch (e: Exception) {
                    binding.errorTV.visibility = View.VISIBLE
                }
            }
        }

        binding.signUpButton.setOnClickListener {
            viewModel.launchSignUp()
        }

    }
}