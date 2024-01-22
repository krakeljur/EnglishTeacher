package com.example.catalog.presentation.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCardBinding
import com.example.catalog.presentation.card.adapters.WordAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        setupObserve()
        setupListeners()
    }

    private fun setupObserve() {
        //todo
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            //todo
        }
        binding.startButton.setOnClickListener {
            //todo
        }
    }
}