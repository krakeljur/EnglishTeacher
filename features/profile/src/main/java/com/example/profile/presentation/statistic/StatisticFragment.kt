package com.example.profile.presentation.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.adapter.DefaultLoadStateAdapter
import com.example.presentation.adapter.TryAgainAction
import com.example.profile.R
import com.example.profile.databinding.FragmentStatisticBinding
import com.example.profile.presentation.statistic.adapter.StatisticAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic) {


    private val viewModel by viewModels<StatisticViewModel>()
    private lateinit var binding: FragmentStatisticBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStatisticBinding.bind(view)

        val adapter = StatisticAdapter()
        val tryAgainAction: TryAgainAction = { adapter.retry() }

        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStat.layoutManager = layoutManager
        binding.recyclerViewStat.adapter = adapterWithLoadState


        observeStatistic(adapter)
        observeLoadState(adapter, tryAgainAction)

    }


    private fun observeStatistic(adapter: StatisticAdapter) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statistic.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun observeLoadState(adapter: StatisticAdapter, tryAgainAction: TryAgainAction) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { combinedLoadState ->
                    when (combinedLoadState.refresh) {
                        is LoadState.NotLoading -> {
                            showSuccess()
                        }

                        is LoadState.Loading -> {
                            showLoading()
                        }

                        is LoadState.Error -> {
                            showError(tryAgainAction)
                        }

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

    private fun showError(tryAgainAction: TryAgainAction) {
        binding.containerView.showError("oops", tryAgainAction)
        binding.constraintLayoutView.visibility = View.GONE
    }
}