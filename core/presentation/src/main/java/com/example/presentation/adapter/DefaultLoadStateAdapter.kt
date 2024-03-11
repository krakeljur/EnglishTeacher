package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.PartLoadingBinding

typealias TryAgainAction = () -> Unit

class DefaultLoadStateAdapter(
    private val tryAgainAction: TryAgainAction
) : LoadStateAdapter<DefaultLoadStateAdapter.DefaultLoadStateViewHolder>() {


    override fun onBindViewHolder(holder: DefaultLoadStateViewHolder, loadState: LoadState) {
        with(holder.binding) {

            progressBar.isVisible = loadState is LoadState.Loading
            errorContainer.isVisible = loadState is LoadState.Error
            errorTextView.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                errorTextView.text = loadState.error.message
                againButton.setOnClickListener { tryAgainAction }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DefaultLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PartLoadingBinding.inflate(inflater, parent, false)
        return DefaultLoadStateViewHolder(binding)
    }


    class DefaultLoadStateViewHolder(
        val binding: PartLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)
}