package com.example.game.presentation.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.game.R
import com.example.game.databinding.FragmentGameBinding
import com.example.game.domain.entities.ResultGame
import com.example.game.domain.entities.WordEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {


    val viewModel by viewModels<GameViewModel>()
    private lateinit var binding: FragmentGameBinding
    private lateinit var currentWord: WordEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        //hardcode for test
        viewModel.init(1)

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
                            if (it.word == null)
                                viewModel.setResult(
                                    ResultGame(
                                        idLesson = 1L,  //hardcode for test
                                        correctCount = it.countCorrect,
                                        wrongCount = it.countWrong,
                                        time = 16000L   //hardcode for test
                                    )
                                )
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