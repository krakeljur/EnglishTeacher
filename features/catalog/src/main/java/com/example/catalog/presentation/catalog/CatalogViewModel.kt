package com.example.catalog.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catalog.domain.AddFavoriteUseCase
import com.example.catalog.domain.DeleteFavoriteUseCase
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.GetFavoritesUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.CatalogRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getCatalogUseCase: GetCatalogUseCase,
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val catalog = getCatalogUseCase.getCatalog().cachedIn(viewModelScope)
    val favorites = getFavoritesUseCase.getFavorites().cachedIn(viewModelScope)


    fun addFavorite(lesson: LessonData) {
        viewModelScope.launch {
            addFavoriteUseCase.addFavorite(lesson)
        }
    }

    fun deleteFavorite(lesson: LessonData) {
        viewModelScope.launch {
            deleteFavoriteUseCase.deleteFavorite(lesson)
        }
    }

    fun launchLesson(lesson: LessonData) {
        catalogRouter.launchCardFromCatalog(lesson)
    }

}