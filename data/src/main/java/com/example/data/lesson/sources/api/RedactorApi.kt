package com.example.data.lesson.sources.api

import com.example.data.lesson.entites.api.AddOrDeleteWordsRequestBody
import com.example.data.lesson.entites.api.CreateLessonRequestBody
import com.example.data.lesson.entites.api.DeleteLessonRequestBody
import com.example.data.lesson.entites.api.PatchLessonRequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.PUT

interface RedactorApi {

    @PUT("lesson")
    suspend fun createLesson(@Body body: CreateLessonRequestBody)

    @PATCH("lesson")
    suspend fun patchLesson(@Body body: PatchLessonRequestBody)

    @DELETE("lesson")
    suspend fun deleteLesson(@Body body: DeleteLessonRequestBody)

    @PUT("word")
    suspend fun addWords(@Body body: AddOrDeleteWordsRequestBody)

    @DELETE("word")
    suspend fun deleteWords(@Body body: AddOrDeleteWordsRequestBody)

}