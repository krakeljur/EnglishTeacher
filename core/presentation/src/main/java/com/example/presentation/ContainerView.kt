package com.example.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.presentation.databinding.PartLoadingBinding

class ContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    private var binding: PartLoadingBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = PartLoadingBinding.inflate(inflater, this, false)
        addView(binding.root)
    }


    fun showPending() {
        hideAll()
        binding.progressBar.visibility = VISIBLE
    }

    fun showError(errorText: String, tryAgain: (() -> Unit)? = null) {
        hideAll()
        binding.errorContainer.visibility = VISIBLE
        binding.errorTextView.text = errorText
        if (tryAgain == null)
            binding.againButton.visibility = GONE
        else {
            binding.againButton.visibility = VISIBLE
            binding.againButton.setOnClickListener { tryAgain }
        }
    }

    fun showSuccess() {
        hideAll()
    }

    private fun hideAll() {
        binding.errorContainer.visibility = GONE
        binding.progressBar.visibility = GONE
    }
}