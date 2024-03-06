package com.example.data.accounts.entities.api

import com.example.data.accounts.entities.AccountDataEntity

data class GetAccountResponseBody(
    val id: String,
    val name: String,
    val login: String
) {

    fun toAccountDataEntity() : AccountDataEntity = AccountDataEntity(
        id,
        name,
        login
    )
}
