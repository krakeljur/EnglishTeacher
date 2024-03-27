package com.example.catalog.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.databinding.ItemLessonBinding
import com.example.catalog.domain.entities.LessonData


interface CatalogActionListener {

    fun launchLesson(lessonData: LessonData)

    fun changeStatus(lessonData: LessonData)

}

class CatalogDiffCallback : DiffUtil.ItemCallback<LessonData>() {
    override fun areItemsTheSame(oldItem: LessonData, newItem: LessonData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LessonData, newItem: LessonData): Boolean =
        oldItem == newItem
}

class CatalogAdapter(
    private val actionListener: CatalogActionListener
) : PagingDataAdapter<LessonData, CatalogAdapter.CatalogViewHolder>(CatalogDiffCallback()),
    View.OnClickListener {

    class CatalogViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val lesson = getItem(position) ?: return

        with(holder.binding) {
            nameTextView.text = lesson.name
            descriptionLessonTextView.text = lesson.description
            countWordText.text = lesson.countWord.toString()
            buttonChangeStatus.tag = lesson
        }

        holder.itemView.tag = lesson
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.buttonChangeStatus.setOnClickListener(this)

        return CatalogViewHolder(binding)
    }

    override fun onClick(v: View) {
        val lesson = v.tag as LessonData

        when (v.id) {
            R.id.buttonChangeStatus -> {
                actionListener.changeStatus(lesson)
            }

            else -> {
                actionListener.launchLesson(lesson)
            }
        }
    }


}