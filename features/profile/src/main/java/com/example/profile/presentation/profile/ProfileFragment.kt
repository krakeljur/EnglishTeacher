package com.example.profile.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.Container
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfile()
        setupListeners()
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profile.collect {
                    with(binding) {
                        when (it) {
                            is Container.Pending -> {
                                containerError.showPending()
                                successGroup.visibility = GONE

                            }

                            is Container.Error -> {
                                containerError.showError(it.message)
                                successGroup.visibility = GONE
                            }

                            is Container.Success -> {
                                containerError.showSuccess()
                                successGroup.visibility = VISIBLE

                                loginTV.text = it.data.login
                                nameTV.text = it.data.name
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        TODO("setup listeners")
    }
}