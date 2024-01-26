package com.example.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profile.domain.EditNameUseCase
import com.example.profile.domain.GetProfileUseCase
import com.example.profile.domain.LogoutUseCase
import com.example.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editNameUseCase: EditNameUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val router: ProfileRouter
) : ViewModel() {

    val profile = getProfileUseCase.getAccount()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.logout()
            router.launchSignInFromProfile()
        }
    }

    fun editName(newName: String) {
        viewModelScope.launch {
            editNameUseCase.editName(newName)
        }
    }

    fun getStatistic(){
        router.launchStatisticFromProfile()
    }
}