package com.example.profile.presentation.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.common.Container
import com.example.profile.R
import com.example.profile.databinding.AlertdialogRenameBinding
import com.example.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), MenuProvider {

    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(setOf(navController.currentDestination!!.id))
        )

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

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
        binding.redactNameButton.setOnClickListener {
            showNameDialog(binding.nameTV.text.toString())
        }
        binding.copyButton.setOnClickListener {
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText(
                getString(com.example.presentation.R.string.user_id),
                binding.idTV.text
            )
            clipboard!!.setPrimaryClip(clip)
        }
    }

    private fun showNameDialog(oldName: String) {
        val dialogBinding = AlertdialogRenameBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(com.example.presentation.R.string.rename_hint))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(com.example.presentation.R.string.save), null)
            .setNegativeButton(getString(com.example.presentation.R.string.cancel), null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.nameEditText.setText(oldName)
            dialogBinding.nameEditText.requestFocus()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.nameEditText.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.nameEditText.error =
                        getString(com.example.presentation.R.string.error)
                    return@setOnClickListener
                } else if (enteredText.length > 18) {
                    dialogBinding.nameEditText.error =
                        getString(com.example.presentation.R.string.very_long_name)
                    return@setOnClickListener
                }

                viewModel.editName(enteredText)

                dialog.dismiss()
            }

            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.profile_toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.logoutMenuItem -> {
                viewModel.logout(requireActivity())
            }

            R.id.statisticMenuItem -> {
                viewModel.getStatistic()
            }
        }
        return true
    }
}
