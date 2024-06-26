package com.example.diploma.trainer_part.users_calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiClientsCalendarTasksBinding
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks.Companion.PATTERN
import com.example.diploma.user_screen.model.TaskListResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserCalendarAdapter : RecyclerView.Adapter<UserCalendarAdapter.TaskUserCalendarViewHolder>() {

    var listener: OnClickListener? = null
    var longClickListener: OnLongClickListener? = null

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
            liClientsCalendarTasksItemTextTime.text = makeTime(item.scheduled)
            root.setOnClickListener {
                listener?.onClick(item)
            }
            root.setOnLongClickListener {
                longClickListener?.onLongClick(item)
                removeItem(item)
                return@setOnLongClickListener true
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
        return String.format("%s:%s", date?.hours, date?.minutes)
    }

    fun removeItem(item: TaskListResponse) {
        val list = items.toMutableList().apply {
            remove(item)
        }
        items = list
        notifyDataSetChanged()
    }

    class TaskUserCalendarViewHolder(val binding: LiClientsCalendarTasksBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(model: TaskListResponse)
    }

    interface OnLongClickListener {
        fun onLongClick(model: TaskListResponse)
    }
}
