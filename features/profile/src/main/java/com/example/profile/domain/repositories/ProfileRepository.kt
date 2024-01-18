package com.example.profile.domain.repositories

import com.example.common.Container
import com.example.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getAccount() : Flow<Container<Profile>>

    suspend fun setUsername(newName: String)

}