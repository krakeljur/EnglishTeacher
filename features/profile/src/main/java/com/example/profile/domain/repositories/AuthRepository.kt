package com.example.profile.domain.repositories

interface AuthRepository {

    suspend fun logout()
}