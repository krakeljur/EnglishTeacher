package com.example.sign_up.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sign_up.domain.SignUpUseCase
import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.presentation.SignUpRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val router: SignUpRouter
) : ViewModel() {

    fun signUp(signUpData: SignUpData) {
        viewModelScope.launch {
            try {
                signUpUseCase.signUp(signUpData)
                router.launchSignIn()
            } catch (e: Exception) {
                throw IllegalStateException()
            }
        }
    }

}