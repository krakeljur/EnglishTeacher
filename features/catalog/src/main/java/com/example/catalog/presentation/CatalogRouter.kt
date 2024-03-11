package com.example.catalog.presentation

import android.os.Bundle

interface CatalogRouter {

    fun launchBackFromCard()

    fun launchGameFromCard(idLesson: String)

    fun launchCardFromCatalog(idLesson: String)

    fun getCardArgs(args: Bundle) : String

}