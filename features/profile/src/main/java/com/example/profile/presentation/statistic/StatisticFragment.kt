package com.example.profile.presentation.statistic

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
import com.example.profile.R
import com.example.profile.databinding.FragmentStatisticBinding
import com.example.profile.presentation.statistic.adapter.StatisticAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {


    val viewModel by viewModels<StatisticViewModel>()
    private lateinit var binding: FragmentStatisticBinding
    private val adapter = StatisticAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStat.layoutManager = layoutManager
        binding.recyclerViewStat.adapter = adapter


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            viewModel.launchProfile()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{
                    if (it.isError) {
                       showError()
                    } else if (it.isLoading) {
                        showLoading()
                    } else {
                        showSuccess()
                        adapter.games = it.statistic
                    }
                }
            }
        }

    }

    private fun showSuccess() {
        binding.containerView.showSuccess()
        binding.constraintLayoutView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.containerView.showPending()
        binding.constraintLayoutView.visibility = View.GONE
    }

    private fun showError() {
        binding.containerView.showError("oops")
        binding.constraintLayoutView.visibility = View.GONE
    }
}