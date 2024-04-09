package com.example.sign_up.presentation.sign_up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sigh_up.R
import com.example.sigh_up.databinding.FragmentSignUpBinding
import com.example.sign_up.domain.entities.SignUpData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment() : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)


        setupListeners()
        setupObserves()
    }

    private fun setupObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    binding.errorSignUpTextView.visibility =
                        if (it.isError) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            signUpButton.setOnClickListener {

                if (loginEditText.text.isBlank())
                    loginEditText.error = getString(com.example.presentation.R.string.error)
                else if (nameEditText.text.isBlank())
                    nameEditText.error = getString(com.example.presentation.R.string.error)
                else if (passwordEditText.text.isBlank())
                    passwordEditText.error = getString(com.example.presentation.R.string.error)
                else {
                    viewModel.signUp(
                        SignUpData(
                            loginEditText.text.toString(),
                            passwordEditText.text.toString(),
                            nameEditText.text.toString()
                        )
                    )
                }

            }
        }
    }
}