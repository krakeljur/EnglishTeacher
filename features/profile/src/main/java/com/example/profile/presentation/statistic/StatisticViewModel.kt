package com.example.profile.presentation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.profile.domain.GetStatisticUseCase
import com.example.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    getStatisticUseCase: GetStatisticUseCase,
    private val router: ProfileRouter
) : ViewModel() {

    val statistic = getStatisticUseCase.getStatistic().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PagingData.empty()
    )


}