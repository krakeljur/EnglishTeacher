package com.example.redactor.presentation.redactor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.redactor.databinding.ItemResultBinding
import com.example.redactor.domain.entities.ResultEntity


class GradeDiffCallBack : DiffUtil.ItemCallback<ResultEntity>() {
    override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean =
        oldItem == newItem

}

class GradeAdapter :
    PagingDataAdapter<ResultEntity, GradeAdapter.GradeViewHolder>(GradeDiffCallBack()) {

    class GradeViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val result = getItem(position) ?: return

        with(holder.binding) {
            nameUser.text = result.nameUser
            timeTV.text = result.time.toString()
            correctTV.text = result.countCorrect.toString()
            wrongTV.text = result.countWrong.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(inflater, parent, false)

        return GradeViewHolder(binding)
    }
}


