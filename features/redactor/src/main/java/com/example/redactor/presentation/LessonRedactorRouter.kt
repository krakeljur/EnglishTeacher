package com.example.redactor.presentation

import android.os.Bundle
import com.example.redactor.domain.entities.LessonEntity

interface LessonRedactorRouter {

    fun getRedactorArgs(args: Bundle): LessonEntity
}