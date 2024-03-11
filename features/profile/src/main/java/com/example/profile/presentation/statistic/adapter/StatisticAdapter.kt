package com.example.profile.presentation.statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.ItemStatisticBinding
import com.example.profile.domain.entities.GameResult
import kotlin.time.Duration.Companion.milliseconds

class StatisticDiffCallBack : DiffUtil.ItemCallback<GameResult>() {
    override fun areItemsTheSame(oldItem: GameResult, newItem: GameResult): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GameResult, newItem: GameResult): Boolean =
        oldItem == newItem

}

class StatisticAdapter :
    PagingDataAdapter<GameResult, StatisticAdapter.StatisticViewHolder>(StatisticDiffCallBack()) {


    class StatisticViewHolder(val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticBinding.inflate(inflater, parent, false)

        return StatisticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val game = getItem(position) ?: return
        with(holder.binding) {
            timeTextView.text = game.time.milliseconds.toString()
            lessonIdTextView.text = game.idLesson
            correctTextView.text = game.countCorrect.toString()
            wrongTextView.text = game.countWrong.toString()
        }
    }
}