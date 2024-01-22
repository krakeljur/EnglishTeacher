package com.example.catalog.presentation.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCardBinding
import com.example.catalog.presentation.card.adapters.WordAdapter
import com.example.common.Container
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card) {

    private val viewModel by viewModels<CardViewModel>()
    private lateinit var binding: FragmentCardBinding
    private val adapter = WordAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(layoutInflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.wordsRecyclerView.layoutManager = layoutManager
        binding.wordsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //HARDCODE FOR TEST
        val idLesson = 1L

        setupObserve(idLesson)
        setupListeners()
    }

    private fun setupObserve(id: Long) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLesson(id).collect {
                    binding.container.showSuccess()
                    when (it) {
                        is Container.Pending -> {
                            binding.cardConstraintLayout.visibility = View.GONE
                            binding.container.showPending()
                        }

                        is Container.Error -> {
                            binding.cardConstraintLayout.visibility = View.GONE
                            binding.container.showError(it.message) { viewModel.goBack() }
                        }

                        is Container.Success -> {
                            binding.cardConstraintLayout.visibility = View.VISIBLE
                            adapter.words = it.data.words
                            binding.descriptionTextView.text = it.data.description
                            binding.idLessonTextView.text = it.data.id.toString()
                            binding.nameLessonTextView.text = it.data.name
                        }
                    }
                }
            }
        }

    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            viewModel.goBack()
        }
        binding.startButton.setOnClickListener {
            viewModel.startGame()
        }
    }
}