package com.example.profile.presentation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.profile.domain.GetStatisticUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    getStatisticUseCase: GetStatisticUseCase
) : ViewModel() {

    val statistic = getStatisticUseCase.getStatistic().cachedIn(viewModelScope)


}