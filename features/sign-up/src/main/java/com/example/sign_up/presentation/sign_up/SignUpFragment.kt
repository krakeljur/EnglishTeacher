package com.example.sign_up.presentation.sign_up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sigh_up.R
import com.example.sigh_up.databinding.FragmentSignUpBinding
import com.example.sign_up.domain.entities.SignUpData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment() : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)


        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            signUpButton.setOnClickListener {
                errorSignUpTextView.visibility = View.GONE

                if (loginEditText.text.isBlank())
                    loginEditText.error = "NOT EMPTY!"
                else if (nameEditText.text.isBlank())
                    nameEditText.error = "NOT EMPTY!"
                else if (passwordEditText.text.isBlank())
                    passwordEditText.error = "NOT EMPTY!"
                else {
                    try {
                        viewModel.signUp(
                            SignUpData(
                                loginEditText.text.toString(),
                                passwordEditText.text.toString(),
                                nameEditText.text.toString()
                            )
                        )
                    } catch (e: Exception) {
                        errorSignUpTextView.visibility = View.VISIBLE
                    }
                }

            }
        }
    }
}