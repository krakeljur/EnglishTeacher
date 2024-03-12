package com.example.catalog.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catalog.R
import com.example.catalog.databinding.ItemLessonBinding
import com.example.catalog.domain.entities.LessonData


interface CatalogActionListener {

    fun launchLesson(lessonData: LessonData)

    fun changeStatus(lessonData: LessonData)

}

class CatalogDiffCallback(
    private val oldList: List<LessonData>,
    private val newList: List<LessonData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLesson = oldList[oldItemPosition]
        val newLesson = newList[newItemPosition]

        return oldLesson.id == newLesson.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLesson = oldList[oldItemPosition]
        val newLesson = newList[newItemPosition]

        return oldLesson.name == newLesson.name && oldLesson.description == newLesson.description
    }
}

class CatalogAdapter(
    private val actionListener: CatalogActionListener
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>(),
    View.OnClickListener {

    var catalog: List<LessonData> = emptyList()
        set(value) {
            val diffCallback = CatalogDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    class CatalogViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonBinding.inflate(inflater)

        binding.root.setOnClickListener(this)
        binding.buttonChangeStatus.setOnClickListener(this)

        return CatalogViewHolder(binding)
    }

    override fun getItemCount(): Int = catalog.size

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val lesson = catalog[position]
        with(holder.binding) {
            holder.itemView.tag = lesson
            buttonChangeStatus.tag = lesson

            nameTextView.text = lesson.name
            descriptionLessonTextView.text = lesson.description
        }
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