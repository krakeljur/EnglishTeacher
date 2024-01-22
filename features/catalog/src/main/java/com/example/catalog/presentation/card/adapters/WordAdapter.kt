package com.example.catalog.presentation.card.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.catalog.databinding.ItemWordBinding
import com.example.catalog.domain.entities.WordData

class WordDiffCallBack(
    private val oldList: List<WordData>,
    private val newList: List<WordData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].eng == newList[newItemPosition].eng
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}

class WordAdapter() : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {


    var words: List<WordData> = emptyList()
        set(newValue) {

            val diffCallBack = WordDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWordBinding.inflate(inflater, parent, false)

        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        with(holder.binding) {
            engTextView.text = word.eng
            rusWordTextView.text = word.russ
        }
    }

    override fun getItemCount(): Int = words.size


    class WordViewHolder(val binding: ItemWordBinding) : ViewHolder(binding.root)

}