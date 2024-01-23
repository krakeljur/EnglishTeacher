package com.example.profile.presentation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Container
import com.example.profile.domain.GetStatisticUseCase
import com.example.profile.domain.entities.GameResult
import com.example.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getStatisticUseCase: GetStatisticUseCase,
    private val router: ProfileRouter
) : ViewModel() {

    private val statistic = getStatisticUseCase.getStatistic()

    val state: StateFlow<StatisticState> = statistic.mapLatest {
        when (it) {
            is Container.Pending -> StatisticState(true, false, emptyList())
            is Container.Error -> StatisticState(false, true, emptyList())
            is Container.Success -> StatisticState(false, false, it.data)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StatisticState(true, false, emptyList())
    )

    fun launchProfile() {
        router.launchProfile()
    }

}

data class StatisticState(
    val isLoading: Boolean,
    val isError: Boolean,
    val statistic: List<GameResult>
)