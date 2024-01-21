package com.example.sign_in.domain.repositories

import kotlinx.coroutines.flow.StateFlow

interface SignInRepository {

    suspend fun signIn(login: String, password: String)

    fun isSign(): StateFlow<Boolean>

}