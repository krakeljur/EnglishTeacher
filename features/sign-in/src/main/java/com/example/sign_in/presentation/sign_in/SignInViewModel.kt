package com.example.sign_in.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sign_in.domain.IsSignedInUseCase
import com.example.sign_in.domain.SignInUseCase
import com.example.sign_in.presentation.SignInRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val router: SignInRouter,
    private val signInUseCase: SignInUseCase,
    private val isSignedInUseCase: IsSignedInUseCase
) : ViewModel() {


    init {
        viewModelScope.launch {
            isSignedInUseCase.isSigned().collect {
                if (it)
                    router.launchFavorites()
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
            } catch (_: Exception) {
                throw IllegalStateException()
            }
        }
    }

}