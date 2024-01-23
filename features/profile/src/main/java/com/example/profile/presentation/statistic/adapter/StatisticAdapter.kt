package com.example.profile.presentation.statistic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.databinding.ItemStatisticBinding
import com.example.profile.domain.entities.GameResult
import kotlin.time.Duration.Companion.milliseconds

class StatisticDiffCallBack(
    private val oldList: List<GameResult>,
    private val newList: List<GameResult>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGame = oldList[oldItemPosition]
        val newGame = newList[newItemPosition]

        return oldGame.idLesson == newGame.idLesson && oldGame.time == newGame.time
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGame = oldList[oldItemPosition]
        val newGame = newList[newItemPosition]
        return oldGame == newGame
    }
}

class StatisticAdapter() : RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder>() {

    var games = emptyList<GameResult>()
        set(value) {
            val diffCallBack = StatisticDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class StatisticViewHolder(val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatisticBinding.inflate(inflater, parent, false)
        val holder = StatisticViewHolder(binding)

        return holder
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val game = games[position]
        with(holder.binding) {
            timeTextView.text = game.time.milliseconds.toString()
            lessonIdTextView.text = game.idLesson.toString()
            correctTextView.text = game.countCorrect.toString()
            wrongTextView.text = game.countWrong.toString()
        }
    }
}