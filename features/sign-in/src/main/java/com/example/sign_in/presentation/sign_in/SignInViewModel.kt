package com.example.sign_in.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sign_in.domain.IsSignedInUseCase
import com.example.sign_in.domain.SignInUseCase
import com.example.sign_in.presentation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val signInUseCase: SignInUseCase,
    private val isSignedInUseCase: IsSignedInUseCase
) : ViewModel() {

    private val _isErrorFlow =
        MutableStateFlow(false)
    val isErrorFlow = _isErrorFlow.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        viewModelScope.launch {
            isSignedInUseCase.isSigned().collect {
                if (it)
                    router.launchTabs()
            }
        }
    }

    fun launchSignUp() {
        router.launchSignUp()
    }

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            try {
                signInUseCase.signIn(login, password)
                _isErrorFlow.value = false
            } catch (_: Exception) {
                _isErrorFlow.value = true
            }
        }
    }

}