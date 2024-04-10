package com.example.englishteacher.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.card.CardFragmentArgs
import com.example.catalog.presentation.card.CardFragmentDirections
import com.example.catalog.presentation.catalog.CatalogFragmentDirections
import com.example.englishteacher.R
import com.example.game.presentation.game.GameFragmentArgs
import com.example.profile.domain.entities.Lesson
import com.example.profile.presentation.profile.ProfileFragmentDirections
import com.example.redactor.domain.entities.LessonEntity
import com.example.redactor.presentation.redactor.LessonRedactorFragmentArgs
import javax.inject.Inject

class RouterImpl @Inject constructor() : Router {

    override var navController: NavController? = null

    override fun returnToCardFromGame() {
        navController?.navigateUp()
    }

    override fun getGameArgs(args: Bundle): String {
        return GameFragmentArgs.fromBundle(args).idLesson
    }


    override fun launchGameFromCard(idLesson: String) {
        val direction = CardFragmentDirections.actionCardFragmentToGameFragment(idLesson)
        navController?.navigate(direction)
    }

    override fun launchCardFromCatalog(lesson: LessonData) {
        val direction = CatalogFragmentDirections.actionCatalogFragmentToCardFragment(
            lesson.id, lesson.name, lesson.description, lesson.idCreator, lesson.isFavorite
        )
        navController?.navigate(direction)
    }

    override fun getCardArgs(args: Bundle): LessonData {
        return LessonData(
            CardFragmentArgs.fromBundle(args).name,
            CardFragmentArgs.fromBundle(args).description,
            CardFragmentArgs.fromBundle(args).idLesson,
            CardFragmentArgs.fromBundle(args).idCreator,
            CardFragmentArgs.fromBundle(args).isFavorite,
        )
    }

    override fun launchStatisticFromProfile() {
        navController?.navigate(R.id.action_profileFragment_to_statisticFragment)
    }


    override fun launchSignInFromProfile(activity: FragmentActivity) {
        val navHost =
            activity.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHost.navController.navigate(R.id.signInFragment, null, navOptions {
            popUpTo(R.id.tabsFragment) {
                inclusive = true
            }
        })
    }

    override fun launchLessonRedactorFromProfile(lesson: Lesson) {
        val direction = ProfileFragmentDirections.actionProfileFragmentToLessonRedactorFragment(
            lesson.id, lesson.name, lesson.description
        )
        navController?.navigate(direction)
    }

    override fun launchSignUp() {
        navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun launchTabs() {
        navController?.navigate(R.id.action_signInFragment_to_tabsFragment)
    }

    override fun goBackToSignInFromSignUp() {
        navController?.navigateUp()
    }

    override fun getRedactorArgs(args: Bundle): LessonEntity {
        val lesson = LessonRedactorFragmentArgs.fromBundle(args)
        return LessonEntity(
            lesson.id, lesson.name, lesson.description
        )
    }

}