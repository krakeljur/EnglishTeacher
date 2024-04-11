package com.example.catalog.presentation.catalog

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalog.R
import com.example.catalog.databinding.FragmentCatalogBinding
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.catalog.adapters.CatalogActionListener
import com.example.catalog.presentation.catalog.adapters.CatalogAdapter
import com.example.presentation.adapter.DefaultLoadStateAdapter
import com.example.presentation.adapter.TryAgainAction
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog), MenuProvider {


    private val viewModel by viewModels<CatalogViewModel>()
    private lateinit var binding: FragmentCatalogBinding

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
    private val adapter = CatalogAdapter(actionListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogBinding.bind(view)


        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.catalogRecyclerView.layoutManager = layoutManager

        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.catalogRecyclerView.adapter = adapterWithLoadState


        setupObservers()
        setupObserveLoadState(tryAgainAction)
    }


    private fun setupObservers() {
        var isFirst = true
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catalog.collectLatest {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isErrorFlow.collectLatest {
                    if (isFirst)
                        isFirst = false
                    else
                        showErrorSnackBar()
                }
            }
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        viewModel.updateDataSource()
        super.onViewStateRestored(savedInstanceState)
    }

    private fun setupObserveLoadState(tryAgainAction: TryAgainAction) {
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

    private fun showErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            (getString(com.example.presentation.R.string.error_oops)).uppercase(),
            Snackbar.LENGTH_SHORT
        )
        snackBar.setActionTextColor(Color.WHITE)
            .setAction(getString(com.example.presentation.R.string.ok)) {
                snackBar.dismiss()
            }

        val snackBarView = snackBar.view
        snackBarView.setBackgroundResource(com.example.presentation.R.drawable.background_error_element)

        snackBar.show()
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.toolbar_catalog_menu, menu)
        val favoriteCheckBox = menu.findItem(R.id.switchFavoriteButton).actionView as CheckBox

        favoriteCheckBox.setButtonDrawable(com.example.presentation.R.drawable.checkbox_selector_favorite)
        favoriteCheckBox.isChecked = viewModel.getCurrentFavorite()
        favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNewFavorite(isChecked)
        }

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            searchEditText.setTextCursorDrawable(com.example.presentation.R.drawable.cursor_drawable)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setNewSearch(query ?: "")
                binding.catalogRecyclerView.scrollToPosition(0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "" || newText == null) {
                    viewModel.setNewSearch("")
                    binding.catalogRecyclerView.scrollToPosition(0)
                }
                return true
            }

        })

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = true


}