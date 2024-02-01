package com.example.game.presentation.game

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.Keys.KEY_CORRECT
import com.example.common.Keys.KEY_TIME
import com.example.common.Keys.KEY_WRONG
import com.example.common.Keys.REQUEST_KEY
import com.example.game.R
import com.example.game.databinding.FragmentGameBinding
import com.example.game.domain.entities.WordEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {


    private val viewModel by viewModels<GameViewModel>()
    private lateinit var binding: FragmentGameBinding
    private lateinit var currentWord: WordEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)
        binding.timer.start()

        viewModel.init(requireArguments())

        setupObserve()
        setupListeners()
    }

    private fun setupObserve() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.gameStateFlow.collect {
                        if (it.isError) {
                            containerView.showError("")
                            constraintLayout.visibility = View.GONE
                        } else if (it.isLoading) {
                            containerView.showPending()
                            constraintLayout.visibility = View.GONE
                        } else {
                            constraintLayout.visibility = View.VISIBLE
                            containerView.showSuccess()
                            if (it.word == null) {
                                val time =
                                    (binding.timer.text.substring(0..1).toLong() * 60L +
                                    binding.timer.text.substring(3..4).toLong()) * 1000L
                                viewModel.setResult(
                                    time = time
                                )
                                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(
                                    KEY_CORRECT to it.countCorrect,
                                    KEY_WRONG to it.countWrong,
                                    KEY_TIME to time
                                ))
                            }
                            else {
                                currentWord = it.word
                                questionTextView.text = currentWord.rus
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            helpButton.setOnClickListener {
                if (firstHintTextView.text == "Hint")
                    firstHintTextView.text = "${currentWord.eng.length} letters"
                else
                    secondHintTextView.text =
                        "first letter is ${currentWord.eng.first().uppercase()}"
            }
            nextButton.setOnClickListener {
                val answer = (editText.text).toString().lowercase().trim()
                if (answer == currentWord.eng.lowercase())
                    viewModel.addCorrect()
                else
                    viewModel.addWrong()
                firstHintTextView.text = "Hint"
                secondHintTextView.text = "Hint"
                editText.setText("")
            }
        }
    }

}