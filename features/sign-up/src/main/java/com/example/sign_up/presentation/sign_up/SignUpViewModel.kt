package com.example.sign_up.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sign_up.domain.SignUpUseCase
import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.presentation.SignUpRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val router: SignUpRouter
) : ViewModel() {

    private val _state : MutableStateFlow<State> = MutableStateFlow(State(false))
    val state : StateFlow<State> = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        State(false)
    )

    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
            try {
                signUpUseCase.signUp(signUpData)
                router.goBackToSignInFromSignUp()
                _state.value = _state.value.copy(isError = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isError = true)
            }
        }
    }

    data class State(
        val isError: Boolean
    )

}