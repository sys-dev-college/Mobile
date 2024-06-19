package com.example.diploma.task_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiTaskDetailItemBinding
import com.example.diploma.user_screen.model.TaskNet

internal class TaskScreenAdapter : RecyclerView.Adapter<TaskScreenAdapter.TaskDetailsViewHolder>() {

    var items: List<TaskNet> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiTaskDetailItemBinding.inflate(inflater, parent, false)
        return TaskDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskDetailsViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liTaskDetailItemText.text = item.name
            liTaskDetailItemTextAmount.text = String.format("%s %s", item.amount, item.unit)
        }
    }

    class TaskDetailsViewHolder(val binding: LiTaskDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}