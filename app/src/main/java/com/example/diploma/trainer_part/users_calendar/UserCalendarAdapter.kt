package com.example.diploma.trainer_part.users_calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiClientsCalendarTasksBinding
import com.example.diploma.user_screen.model.TaskListResponse

class UserCalendarAdapter : RecyclerView.Adapter<UserCalendarAdapter.TaskUserCalendarViewHolder>() {

    var listener: OnClickListener? = null

    var items: List<TaskListResponse> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskUserCalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiClientsCalendarTasksBinding.inflate(inflater, parent, false)
        return TaskUserCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskUserCalendarViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liClientsCalendarTasksItemText.text = item.title
            liClientsCalendarTasksItemText.setOnClickListener {
                listener?.onClick(item)
            }
            /*liClientsCalendarTasksItemCheckbox.setOnCheckedChangeListener { listener?.onClick(item) }*/
            }

    }

    class TaskUserCalendarViewHolder(val binding: LiClientsCalendarTasksBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(model: TaskListResponse)
    }
}
