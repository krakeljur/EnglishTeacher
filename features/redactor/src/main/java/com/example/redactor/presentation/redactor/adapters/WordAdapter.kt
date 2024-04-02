package com.example.redactor.presentation.redactor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.redactor.R
import com.example.redactor.databinding.ItemWordRedactorBinding
import com.example.redactor.domain.entities.WordEntity


class WordDiffCallBack(
    private val oldList: List<WordEntity>,
    private val newList: List<WordEntity>
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

interface ActionListener {
    fun deleteWord(wordEntity: WordEntity)
}

class WordAdapter(
    private val actionListener: ActionListener
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>(), View.OnClickListener {

    var words: List<WordEntity> = emptyList()
        set(value) {
            val diffCallBack = WordDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class WordViewHolder(val binding: ItemWordRedactorBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWordRedactorBinding.inflate(inflater, parent, false)

        binding.deleteWordButton.setOnClickListener(this)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        with(holder.binding) {
            engTextView.text = word.eng
            rusWordTextView.text = word.rus
            deleteWordButton.tag = word
        }
    }

    override fun getItemCount(): Int = words.size

    override fun onClick(v: View) {
        val word = v.tag as WordEntity
        when (v.id) {
            R.id.deleteWordButton -> {
                actionListener.deleteWord(word)
            }
        }
    }
}