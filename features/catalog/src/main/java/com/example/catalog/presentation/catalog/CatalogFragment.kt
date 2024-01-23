package com.example.catalog.presentation.catalog

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
import com.example.catalog.databinding.FragmentCatalogBinding
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.catalog.adapters.CatalogActionListener
import com.example.catalog.presentation.catalog.adapters.CatalogAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {


    val viewModel by viewModels<CatalogViewModel>()
    private lateinit var binding: FragmentCatalogBinding
    private lateinit var adapter: CatalogAdapter
    private var lastCatalog = emptyList<LessonData>()
    private var lastFavorite = emptyList<LessonData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(layoutInflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.catalogRecyclerView.layoutManager = layoutManager

        adapter = CatalogAdapter(object : CatalogActionListener {
            override fun launchLesson(id: Long) {
                viewModel.launchLesson()
            }

            override fun changeStatus(id: Long) {
                if (binding.switchFavorite.isActivated)
                    viewModel.deleteFavorite(id)
                else
                    viewModel.addFavorite(id)
            }
        }
        )
        binding.catalogRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupListeners()
        setupObserves()
    }

    private fun setupObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.isError) {
                        error("oops") { viewModel.update() }
                    } else if (it.isLoading) {
                        pending()
                    } else {
                        success()
                        lastCatalog = it.catalog
                        lastFavorite = it.favorites

                        if (binding.switchFavorite.isChecked) {
                            adapter.catalog = it.favorites
                        } else {
                            adapter.catalog = it.catalog
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

    private fun setupListeners() {
        binding.switchFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                adapter.catalog = lastFavorite
            else
                adapter.catalog = lastCatalog
        }
    }
}