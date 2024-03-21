package com.example.catalog.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.catalog.domain.AddFavoriteUseCase
import com.example.catalog.domain.DeleteFavoriteUseCase
import com.example.catalog.domain.GetCatalogUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.CatalogRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getCatalogUseCase: GetCatalogUseCase
) : ViewModel() {


    private val isFavoriteFlow = MutableStateFlow(false)
    private val searchByFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val catalog = combine(isFavoriteFlow, searchByFlow) { isFavorite, searchBy ->
        Pair(isFavorite, searchBy)
    }
        .flatMapLatest { (isFavorite, searchBy) ->
            getCatalogUseCase.getCatalog(isFavorite, searchBy)
        }
        .cachedIn(viewModelScope)


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

    fun setNewFavorite(flag: Boolean) {
        isFavoriteFlow.value = flag
    }

    fun setNewSearch(query : String) {
        searchByFlow.value = query
    }

    fun getCurrentFavorite() : Boolean = isFavoriteFlow.value
    fun launchLesson(lesson: LessonData) {
        catalogRouter.launchCardFromCatalog(lesson)
    }



}