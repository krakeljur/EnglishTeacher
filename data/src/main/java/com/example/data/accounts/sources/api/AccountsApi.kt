package com.example.data.accounts.sources.api

import com.example.data.accounts.entities.api.GetAccountRequestBody
import com.example.data.accounts.entities.api.GetAccountResponseBody
import com.example.data.accounts.entities.api.LogoutRequestBody
import com.example.data.accounts.entities.api.RenameUserRequestBody
import com.example.data.accounts.entities.api.SignInRequestBody
import com.example.data.accounts.entities.api.SignInResponseBody
import com.example.data.accounts.entities.api.SignUpRequestBody
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AccountsApi {

    @POST("login")
    suspend fun singIn(@Body body: SignInRequestBody): SignInResponseBody

    @PATCH("user")
    suspend fun renameUser(@Body body: RenameUserRequestBody)

    @POST("signup")
    suspend fun signUp(@Body body: SignUpRequestBody)

    @POST("logout")
    suspend fun logout(@Body body: LogoutRequestBody)

    @POST("user")
    suspend fun getAccount(@Body body: GetAccountRequestBody) : GetAccountResponseBody
}