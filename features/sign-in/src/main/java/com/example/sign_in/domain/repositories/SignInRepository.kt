package com.example.sign_in.domain.repositories

interface SignInRepository {

    suspend fun signIn(login: String, password: String)

    fun isSign(): Boolean

}