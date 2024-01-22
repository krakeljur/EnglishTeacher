package com.example.catalog.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.AddFavoriteUseCase
import com.example.catalog.domain.DeleteFavoriteUseCase
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.GetFavoritesUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.Router
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val router: Router,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getCatalogUseCase: GetCatalogUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    fun getCatalog() : Flow<Container<List<LessonData>>> {
        return getCatalogUseCase.getCatalog()
    }

    fun getFavorite() : Flow<Container<List<LessonData>>> {
        return getFavoritesUseCase.getFavorites()
    }


    fun addFavorite(id: Long) {
        viewModelScope.launch {
            addFavoriteUseCase.addFavorite(id)
        }
    }

    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            deleteFavoriteUseCase.deleteFavorite(id)
        }
    }

    fun launchLesson() {
        router.launchCard()
    }




}