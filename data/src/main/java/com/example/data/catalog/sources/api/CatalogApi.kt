package com.example.data.catalog.sources.api

import com.example.data.catalog.entities.api.AddOrDeleteFavoriteRequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface CatalogApi {

    @POST("favorite")
    suspend fun addFavorite(@Body body: AddOrDeleteFavoriteRequestBody)

    @DELETE("favorite")
    suspend fun deleteFavorite(@Body body: AddOrDeleteFavoriteRequestBody)


}