package com.example.profile.presentation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.profile.domain.GetStatisticUseCase
import com.example.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    getStatisticUseCase: GetStatisticUseCase,
    private val router: ProfileRouter
) : ViewModel() {

    val statistic = getStatisticUseCase.getStatistic().cachedIn(viewModelScope)


}