package com.example.redactor.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.redactor.R
import com.example.redactor.databinding.FragmentLessonRedactorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonRedactorFragment : Fragment(R.layout.fragment_lesson_redactor) {

    private val viewModel by viewModels<LessonRedactorViewModel>()
    private lateinit var binding: FragmentLessonRedactorBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLessonRedactorBinding.bind(view)
    }
}