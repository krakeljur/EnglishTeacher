package com.example.englishteacher.navigation

import androidx.navigation.NavController
import com.example.englishteacher.R
import javax.inject.Inject

class RouterImpl @Inject constructor(): Router {

    override var navController: NavController? = null
    //WILL BE REFACTOR IN FUTURE IT IMPLEMENTATION FOR TESTS
    override fun returnToCardFromGame() {
        navController?.popBackStack()
    }

    override fun launchBackFromCard() {
        navController?.popBackStack()
    }

    override fun launchGameFromCard() {
        navController?.navigate(R.id.action_cardFragment_to_gameFragment)
    }

    override fun launchCardFromCatalog() {
        navController?.navigate(R.id.action_catalogFragment_to_cardFragment)
    }

    override fun launchStatisticFromProfile() {
        navController?.navigate(R.id.action_profileFragment_to_statisticFragment)
    }

    override fun goBackToProfileFromStatistic() {
        navController?.popBackStack()
    }

    override fun launchSignInFromProfile() {
        navController?.popBackStack(R.id.profileFragment, true)
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