package com.example.diploma.user_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiTaskItemBinding
import com.example.diploma.user_screen.model.TaskListResponse

internal class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    var listener: OnClickListener? = null

    var items: List<TaskListResponse> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiTaskItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liTaskItemText.text = item.title
            liTaskItemText.setOnClickListener {
                listener?.onClick(item)
            }
        }
    }

    class TaskViewHolder(val binding: LiTaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(model: TaskListResponse)
    }
}