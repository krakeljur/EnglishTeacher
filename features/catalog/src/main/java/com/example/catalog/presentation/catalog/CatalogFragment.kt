package com.example.catalog.presentation.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCatalogBinding
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.catalog.adapters.CatalogActionListener
import com.example.catalog.presentation.catalog.adapters.CatalogAdapter
import com.example.presentation.adapter.DefaultLoadStateAdapter
import com.example.presentation.adapter.TryAgainAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {


    private val viewModel by viewModels<CatalogViewModel>()
    private lateinit var binding: FragmentCatalogBinding
    private var lastCatalog = PagingData.empty<LessonData>()
    private var lastFavorite = PagingData.empty<LessonData>()

    private val actionListener = object : CatalogActionListener {
        override fun launchLesson(lessonData: LessonData) {
            viewModel.launchLesson(lessonData)
        }

        override fun changeStatus(lessonData: LessonData) {
            if (lessonData.isFavorite) {
                viewModel.deleteFavorite(lessonData)
            } else {
                viewModel.addFavorite(lessonData)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.catalogRecyclerView.layoutManager = layoutManager

        val adapter = CatalogAdapter(actionListener)
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.catalogRecyclerView.adapter = adapterWithLoadState


        setupObserveLessonData(adapter)
        setupObserveLoadState(adapter, tryAgainAction)
        setupListeners(adapter)
    }


    private fun setupObserveLessonData(adapter: CatalogAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catalog.collectLatest {
                    lastCatalog = it
                    if (!binding.switchFavorite.isChecked)
                        adapter.submitData(lifecycle, it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collectLatest {
                    lastFavorite = it
                    if (binding.switchFavorite.isChecked)
                        adapter.submitData(lifecycle, it)

                }
            }
        }
    }

    private fun setupObserveLoadState(adapter: CatalogAdapter, tryAgainAction: TryAgainAction) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { combinedLoadState ->
                    when (combinedLoadState.refresh) {
                        is LoadState.NotLoading -> {
                            success()
                        }

                        is LoadState.Loading -> {
                            pending()
                        }

                        is LoadState.Error -> {
                            error(
                                (combinedLoadState.refresh as LoadState.Error).error.message
                                    ?: getString(com.example.presentation.R.string.error),
                                tryAgainAction
                            )
                        }
                    }
                }
            }
        }
    }

    private fun pending() {
        binding.constraintLayout.visibility = View.GONE
        binding.containerView.showPending()
    }

    private fun error(message: String, tryAgain: (() -> Unit)? = null) {
        binding.constraintLayout.visibility = View.GONE
        binding.containerView.showError(message, tryAgain)
    }

    private fun success() {
        binding.constraintLayout.visibility = View.VISIBLE
        binding.containerView.showSuccess()
    }

    private fun setupListeners(adapter: CatalogAdapter) {
        binding.switchFavorite.setOnCheckedChangeListener { _, isChecked ->
            val newData = if (isChecked) lastFavorite else lastCatalog
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.refresh()
                adapter.submitData(lifecycle, newData)
            }
        }
    }
}