package com.example.catalog.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.UpdateFlowsUseCase
import com.example.catalog.domain.AddFavoriteUseCase
import com.example.catalog.domain.DeleteFavoriteUseCase
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.GetFavoritesUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.CatalogRouter
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val updateFlowsUseCase: UpdateFlowsUseCase
) : ViewModel() {

    private val catalog = getCatalogUseCase.getCatalog()

    private val favorites = getFavoritesUseCase.getFavorites()

    private val loading = MutableStateFlow(0)

    val state = combine(catalog, favorites, loading) { cat, fav, loading ->
        CatalogState(
            cat is Container.Error || fav is Container.Error,
            cat is Container.Pending || fav is Container.Pending || loading > 0,
            if (fav is Container.Success) fav.data else emptyList(),
            if (cat is Container.Success) cat.data else emptyList()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CatalogState(
            false,
            true,
            emptyList(),
            emptyList()
        )
    )

    fun addFavorite(id: Long) {
        viewModelScope.launch {
            withLoading {
                addFavoriteUseCase.addFavorite(id)
            }
        }
    }

    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            withLoading {
                deleteFavoriteUseCase.deleteFavorite(id)
            }
        }
    }

    fun launchLesson() {
        catalogRouter.launchCard()
    }

    fun update() {
        viewModelScope.launch {
            withLoading {
                updateFlowsUseCase.update()
            }
        }
    }


    private suspend fun withLoading(block: suspend () -> Unit) {
        try {
            loading.value += 1
            block()
        } finally {
            loading.value -= 1
        }
    }


}

data class CatalogState(
    val isError: Boolean,
    val isLoading: Boolean,
    val favorites: List<LessonData>,
    val catalog: List<LessonData>
)