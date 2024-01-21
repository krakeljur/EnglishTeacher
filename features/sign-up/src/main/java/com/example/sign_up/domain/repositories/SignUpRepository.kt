package com.example.sign_up.domain.repositories

import com.example.sign_up.domain.entities.SignUpData

interface SignUpRepository {

    suspend fun signUp(signUpData: SignUpData)
}