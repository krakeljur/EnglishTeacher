package com.example.data.game.sources.api

import com.example.data.game.etities.api.GetResultsRequestBody
import com.example.data.game.etities.api.GetResultsResponseBody
import com.example.data.game.etities.api.PutResultRequestBody
import com.example.data.game.etities.api.PutResultResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ResultApi {

    @POST("result")
    suspend fun getResults(@Body body: GetResultsRequestBody): GetResultsResponseBody

    @PUT("result")
    suspend fun setResult(@Body body: PutResultRequestBody) : PutResultResponseBody

}