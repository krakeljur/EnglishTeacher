package com.example.redactor.presentation.redactor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.adapter.DefaultLoadStateAdapter
import com.example.presentation.adapter.TryAgainAction
import com.example.redactor.R
import com.example.redactor.databinding.AlertdialogCreateWordBinding
import com.example.redactor.databinding.AlertdialogRenameLessonBinding
import com.example.redactor.databinding.FragmentLessonRedactorBinding
import com.example.redactor.domain.entities.WordEntity
import com.example.redactor.presentation.redactor.adapters.ActionListener
import com.example.redactor.presentation.redactor.adapters.GradeAdapter
import com.example.redactor.presentation.redactor.adapters.WordAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LessonRedactorFragment : Fragment(R.layout.fragment_lesson_redactor), MenuProvider {

    private val viewModel by viewModels<LessonRedactorViewModel>()
    private lateinit var binding: FragmentLessonRedactorBinding

    private val wordAdapter = WordAdapter(object : ActionListener {
        override fun deleteWord(wordEntity: WordEntity) {
            viewModel.deleteWord(wordEntity)
        }
    })

    private val gradesAdapter = GradeAdapter()
    private lateinit var adapterWithLoadState: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLessonRedactorBinding.bind(view)

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        viewModel.init(requireArguments())

        binding.recyclerViewRedactor.layoutManager = LinearLayoutManager(requireContext())
        val tryAgainAction: TryAgainAction = { gradesAdapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        adapterWithLoadState = gradesAdapter.withLoadStateFooter(footerAdapter)

        setupObserve()
        setupListeners()

    }

    private fun setupListeners() {
        binding.copyButton.setOnClickListener {
            val clipboard =
                ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText(
                getString(com.example.presentation.R.string.lesson_id), binding.idTV.text
            )
            clipboard!!.setPrimaryClip(clip)
            val snackBar = Snackbar.make(
                binding.root,
                getString(com.example.presentation.R.string.copy_notification_lesson),
                Snackbar.LENGTH_SHORT
            )

            snackBar.setAction(getString(com.example.presentation.R.string.ok)) {
                snackBar.dismiss()
            }

            snackBar.show()
        }

        binding.redactLessonButton.setOnClickListener {
            showRenameDialog(binding.nameTV.text.toString(), binding.descriptionTV.text.toString())
        }

    }

    private fun showRenameDialog(name: String, description: String) {
        val dialogBinding = AlertdialogRenameLessonBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(com.example.presentation.R.string.redactor_lesson))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(com.example.presentation.R.string.save), null)
            .setNegativeButton(getString(com.example.presentation.R.string.cancel), null).create()

        dialog.setOnShowListener {
            dialogBinding.nameLessonEditText.setText(name)
            dialogBinding.descriptionLessonEditText.setText(description)
            dialogBinding.nameLessonEditText.requestFocus()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredName = dialogBinding.nameLessonEditText.text.toString()
                val enteredDescription = dialogBinding.descriptionLessonEditText.text.toString()

                if (enteredName.isBlank()) {
                    dialogBinding.nameLessonEditText.error =
                        getString(com.example.presentation.R.string.error)
                    return@setOnClickListener
                }
                if (enteredName.length > 50) {
                    dialogBinding.nameLessonEditText.error =
                        getString(com.example.presentation.R.string.very_long_name)
                    return@setOnClickListener
                }

                if (enteredDescription.isBlank()) {
                    dialogBinding.descriptionLessonEditText.error =
                        getString(com.example.presentation.R.string.error)
                    return@setOnClickListener
                }
                if (enteredDescription.length > 150) {
                    dialogBinding.descriptionLessonEditText.error =
                        getString(com.example.presentation.R.string.very_long_description)
                    return@setOnClickListener
                }

                viewModel.patchLesson(enteredName, enteredDescription)

                dialog.dismiss()
            }

            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setupObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateRedactor.collectLatest {
                    if (it.isError) {
                        showError()
                    } else if (it.isLoading) {
                        showLoading()
                    } else {
                        with(it.lesson!!) {
                            binding.nameTV.text = name
                            binding.idTV.text = id
                            binding.descriptionTV.text = description
                        }
                        wordAdapter.words = it.words
                        showSuccess()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statisticFlow.collectLatest {
                    gradesAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getGradeFlow().collectLatest {
                    binding.recyclerViewRedactor.adapter = if (it)
                        adapterWithLoadState
                    else
                        wordAdapter

                }
            }
        }
    }

    private fun showSuccess() {
        binding.errorContainer.showSuccess()
        binding.successGroup.visibility = View.VISIBLE
        binding.successGroupBottom.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.errorContainer.showPending()
        binding.successGroup.visibility = View.GONE
        binding.successGroupBottom.visibility = View.GONE
    }

    private fun showError() {
        binding.errorContainer.showError(getString(com.example.presentation.R.string.error_oops)) {viewModel.init(requireArguments())}
        binding.successGroup.visibility = View.GONE
        binding.successGroupBottom.visibility = View.GONE
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_redactor, menu)

        val statisticCheckBox = menu.findItem(R.id.switchGradeButton).actionView as CheckBox

        statisticCheckBox.setButtonDrawable(com.example.presentation.R.drawable.checkbox_selector_grades)
        statisticCheckBox.isChecked = viewModel.getCurrentGradeStatus()
        statisticCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeGradeStatus(isChecked)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.addWordMenuItem -> {
                showAddWordDialog()
            }

        }
        return true
    }

    private fun showAddWordDialog() {
        val dialogBinding = AlertdialogCreateWordBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(com.example.presentation.R.string.create_word))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(com.example.presentation.R.string.save), null)
            .setNegativeButton(getString(com.example.presentation.R.string.cancel), null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.rusEditText.requestFocus()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val rusWord = dialogBinding.rusEditText.text.toString()
                val engWord = dialogBinding.engWordEditText.text.toString()

                if (rusWord.isBlank()) {
                    dialogBinding.rusEditText.error =
                        getString(com.example.presentation.R.string.error)
                    return@setOnClickListener
                }
                if (engWord.isBlank()) {
                    dialogBinding.engWordEditText.error =
                        getString(com.example.presentation.R.string.error)
                    return@setOnClickListener
                }
                if (rusWord.length > 60) {
                    dialogBinding.rusEditText.error =
                        getString(com.example.presentation.R.string.very_long_word)
                    return@setOnClickListener
                }
                if (engWord.length > 60) {
                    dialogBinding.engWordEditText.error =
                        getString(com.example.presentation.R.string.very_long_word)
                    return@setOnClickListener
                }

                viewModel.addWord(rusWord.lowercase(), engWord.lowercase())

                dialog.dismiss()
            }

            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}