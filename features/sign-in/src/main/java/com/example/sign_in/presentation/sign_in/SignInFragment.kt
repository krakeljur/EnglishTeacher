package com.example.sign_in.presentation.sign_in

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.Const
import com.example.sign_in.R
import com.example.sign_in.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()
    private lateinit var binding: FragmentSignInBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)


        setupListeners()
        setupObserves()
    }

    private fun setupObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isErrorFlow.collectLatest {
                    binding.errorTV.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun setupListeners() {
        Log.i(Const.LOG_TAG, viewModel.toString())
        binding.signInButton.setOnClickListener {
            if (binding.loginEditText.text.isBlank())
                binding.loginEditText.error = getString(com.example.presentation.R.string.error)
            else if (binding.passwordEditText.text.isBlank())
                binding.passwordEditText.error = getString(com.example.presentation.R.string.error)
            else {
                viewModel.signIn(
                    binding.loginEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }

        binding.signUpButton.setOnClickListener {
            viewModel.launchSignUp()
        }

        binding.loginEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.loginEditText.hint = if (hasFocus) "" else getString(com.example.presentation.R.string.login)
        }
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            binding.passwordEditText.hint = if (hasFocus) "" else getString(com.example.presentation.R.string.password)
        }
    }
}