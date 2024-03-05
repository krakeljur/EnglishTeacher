package com.example.data.accounts.entities.api

data class RenameUserRequestBody(
    val token: String,
    val newName: String
)
