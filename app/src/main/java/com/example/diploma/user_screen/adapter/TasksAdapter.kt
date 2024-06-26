package com.example.diploma.user_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiTaskItemBinding
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks.Companion.PATTERN
import com.example.diploma.user_screen.model.TaskListResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            liTasksItemText.text = item.title
            liTasksItemTextTime.text = makeTime(item.scheduled)
            root.setOnClickListener {
                listener?.onClick(item)
            }
        }
    }

    private fun makeTime(timeString: String?): String {
        val sdf = SimpleDateFormat(PATTERN, Locale.getDefault())
        val date =
            try {
                timeString?.let { dateString ->
                    sdf.parse(dateString)
                }
            } catch (e: ParseException) {
                Date()
            }
        val dateHours = date?.hours
        val dateMinutes = date?.minutes
        val hourString = when (dateHours.toString().length == 1) {
            true -> "0$dateHours"
            false -> "$dateHours"
        }
        val minuteString = when (dateMinutes.toString().length == 1) {
            true -> "0$dateMinutes"
            false -> "$dateMinutes"
        }
        return String.format("%s:%s", hourString, minuteString)
    }

    class TaskViewHolder(val binding: LiTaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(model: TaskListResponse)
    }
}