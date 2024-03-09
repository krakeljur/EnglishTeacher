package com.example.data.catalog.sources.api

import com.example.data.catalog.entities.api.AddOrDeleteFavoriteRequestBody
import com.example.data.catalog.entities.api.GetCatalogRequestBody
import com.example.data.catalog.entities.api.GetCatalogResponseBody
import com.example.data.catalog.entities.api.GetFavoriteRequestBody
import com.example.data.catalog.entities.api.GetFavoriteResponseBody
import com.example.data.catalog.entities.api.GetWordsRequestBody
import com.example.data.catalog.entities.api.GetWordsResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface CatalogApi {

    @POST("favorite")
    suspend fun addFavorite(@Body body: AddOrDeleteFavoriteRequestBody)

    @DELETE("favorite")
    suspend fun deleteFavorite(@Body body: AddOrDeleteFavoriteRequestBody)

    @PUT("favorite")
    suspend fun getFavoritesLesson(@Body body: GetFavoriteRequestBody): GetFavoriteResponseBody

    @POST("word")
    suspend fun getWords(@Body body: GetWordsRequestBody): GetWordsResponseBody

    @POST("catalog")
    suspend fun getCatalog(@Body body: GetCatalogRequestBody): GetCatalogResponseBody

}