package com.example.data.accounts.entities.api

data class SignUpRequestBody(
    val name: String,
    val login: String,
    val password: String
)
