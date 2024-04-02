package com.example.profile.presentation.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.R
import com.example.profile.databinding.ItemLessonInProfileBinding
import com.example.profile.domain.entities.Lesson


class LessonDiffCallBack : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
        oldItem == newItem

}

class LessonAdapter(
    private val actionListener: LessonActionListener,
) :
    PagingDataAdapter<Lesson, LessonAdapter.LessonViewHolder>(LessonDiffCallBack()),
    View.OnClickListener {
    class LessonViewHolder(val binding: ItemLessonInProfileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = getItem(position) ?: return

        with(holder.binding) {
            nameTextView.text = lesson.name
            descriptionLessonTextView.text = lesson.description
            buttonDelete.tag = lesson
        }

        holder.itemView.tag = lesson
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLessonInProfileBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)

        return LessonViewHolder(binding)
    }

    override fun onClick(v: View) {
        val lesson = v.tag as Lesson

        when (v.id) {
            R.id.buttonDelete -> {
                actionListener.deleteLesson(lesson)
            }

            else -> {
                actionListener.launchRedactor(lesson)
            }
        }
    }
}

interface LessonActionListener {

    fun launchRedactor(lesson: Lesson)

    fun deleteLesson(lesson: Lesson)

}