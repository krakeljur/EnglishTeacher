package com.example.profile.presentation

import androidx.fragment.app.FragmentActivity
import com.example.profile.domain.entities.Lesson

interface ProfileRouter {

    fun launchStatisticFromProfile()

    fun launchSignInFromProfile(activity: FragmentActivity)

    fun launchLessonRedactorFromProfile(lesson: Lesson)

}