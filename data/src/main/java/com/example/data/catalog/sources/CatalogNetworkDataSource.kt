package com.example.data.catalog.sources

interface CatalogNetworkDataSource {

    suspend fun addFavorite(idLesson: String, token: String)

    suspend fun deleteFavorite(idLesson: String, token: String)


}