package com.example.sign_in.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(login: String, password: String)

    fun isSign(): Flow<Boolean>

}