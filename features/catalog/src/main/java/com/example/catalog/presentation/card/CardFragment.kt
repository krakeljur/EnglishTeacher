package com.example.catalog.presentation.card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCardBinding
import com.example.catalog.presentation.card.adapters.WordAdapter
import com.example.common.Keys.KEY_CORRECT
import com.example.common.Keys.KEY_TIME
import com.example.common.Keys.KEY_WRONG
import com.example.common.Keys.REQUEST_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card) {

    private val viewModel by viewModels<CardViewModel>()
    private lateinit var binding: FragmentCardBinding
    private val adapter = WordAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCardBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.wordsRecyclerView.layoutManager = layoutManager
        binding.wordsRecyclerView.adapter = adapter


        viewModel.init(requireArguments())

        setupObserve()
        setupListeners()
    }

    private fun setupObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateCard.collect {
                    binding.container.showSuccess()
                    if (it.isLoading) {
                        binding.cardConstraintLayout.visibility = View.GONE
                        binding.container.showPending()
                    } else if (it.isError || it.lesson == null) {
                        binding.cardConstraintLayout.visibility = View.GONE
                        binding.container.showError("it.message") { viewModel.goBack() }
                    } else {
                        binding.cardConstraintLayout.visibility = View.VISIBLE
                        adapter.words = it.lesson.words
                        binding.descriptionTextView.text = it.lesson.description
                        binding.idLessonTextView.text = it.lesson.id.toString()
                        binding.nameLessonTextView.text = it.lesson.name
                    }
                }
            }
        }
    }


    private fun setupListeners() {
        binding.startButton.setOnClickListener {
            viewModel.startGame()
        }
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, data ->
            val correctResult = data.getInt(KEY_CORRECT)
            val wrongResult = data.getInt(KEY_WRONG)
            val time = data.getLong(KEY_TIME)
            val finalString =
                getString(com.example.presentation.R.string.correct) + " - $correctResult; " +
                        getString(com.example.presentation.R.string.wrong) + " - $wrongResult; " +
                        getString(com.example.presentation.R.string.time) + " - $time"

            val snackBar = Snackbar.make(binding.root, finalString, Snackbar.LENGTH_LONG)

            snackBar.setAction(getString(com.example.presentation.R.string.ok)) {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }
}