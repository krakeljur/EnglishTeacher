package com.example.englishteacher.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.catalog.presentation.card.CardFragmentArgs
import com.example.catalog.presentation.card.CardFragmentDirections
import com.example.catalog.presentation.catalog.CatalogFragmentDirections
import com.example.englishteacher.R
import com.example.game.presentation.game.GameFragmentArgs
import javax.inject.Inject

class RouterImpl @Inject constructor() : Router {

    override var navController: NavController? = null

    override fun returnToCardFromGame() {
        navController?.popBackStack()
    }

    override fun getGameArgs(args: Bundle): Long {
        return GameFragmentArgs.fromBundle(args).idLesson
    }

    override fun launchBackFromCard() {
        navController?.popBackStack()
    }

    override fun launchGameFromCard(idLesson: Long) {
        val direction = CardFragmentDirections.actionCardFragmentToGameFragment(idLesson)
        navController?.navigate(direction)
    }

    override fun launchCardFromCatalog(idLesson: Long) {
        val direction = CatalogFragmentDirections.actionCatalogFragmentToCardFragment(idLesson)
        navController?.navigate(direction)
    }

    override fun getCardArgs(args: Bundle): Long {
        return CardFragmentArgs.fromBundle(args).idLesson
    }

    override fun launchStatisticFromProfile() {
        navController?.navigate(R.id.action_profileFragment_to_statisticFragment)
    }


    override fun launchSignInFromProfile() {
        navController?.navigate(R.id.signInFragment, null, navOptions {
            popUpTo(R.id.profileFragment) {
                inclusive = true
            }
        })
    }

    override fun launchSignUp() {
        navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun launchCatalog() {
        navController?.navigate(R.id.action_signInFragment_to_catalogFragment)
    }

    override fun goBackToSignInFromSignUp() {
        navController?.popBackStack()
    }

}