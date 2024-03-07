package com.example.data.catalog.sources

import com.example.data.catalog.entities.api.AddOrDeleteFavoriteRequestBody
import com.example.data.catalog.sources.api.CatalogApi
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitCatalogDataSource @Inject constructor(
    retrofit: Retrofit
) : CatalogNetworkDataSource {

    private val catalogApi = retrofit.create(CatalogApi::class.java)
    override suspend fun addFavorite(idLesson: String, token: String) {
        catalogApi.addFavorite(
            AddOrDeleteFavoriteRequestBody(
                token,
                idLesson
            )
        )
    }

    override suspend fun deleteFavorite(idLesson: String, token: String) {
        catalogApi.deleteFavorite(
            AddOrDeleteFavoriteRequestBody(
                token,
                idLesson
            )
        )
    }
}