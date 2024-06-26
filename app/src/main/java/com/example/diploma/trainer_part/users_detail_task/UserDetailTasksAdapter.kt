package com.example.diploma.trainer_part.users_detail_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiTaskDetailItemBinding
import com.example.diploma.user_screen.model.TaskNet

class UserDetailTasksAdapter :
    RecyclerView.Adapter<UserDetailTasksAdapter.UserDetailTaskViewHolder>() {

    var onItemClick: OnCheckBoxClick? = null
    var longClickListener: OnLongClickListener? = null
    var items: List<TaskNet> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiTaskDetailItemBinding.inflate(inflater, parent, false)
        return UserDetailTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDetailTaskViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liTaskDetailItemText.text = item.name
            liTaskDetailItemTextAmount.text = String.format("%s %s", item.amount, item.unit)
            /*taskDetailItemCheckbox.isChecked = item.completed
            root.setOnClickListener {
                taskDetailItemCheckbox.isChecked = !taskDetailItemCheckbox.isChecked
                item.completed = taskDetailItemCheckbox.isChecked
                onItemClick?.invoke(item)
            }*/
            liTaskDetailItemText.setOnLongClickListener {
                longClickListener?.onLongClick(item)
                removeItem(item)
                return@setOnLongClickListener true
            }
        }
    }

    fun removeItem(item: TaskNet) {
        val list = items.toMutableList().apply {
            remove(item)
        }
        items = list
        notifyDataSetChanged()
    }

    class UserDetailTaskViewHolder(val binding: LiTaskDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCheckBoxClick {
        fun invoke(item: TaskNet)
    }

    interface OnLongClickListener {
        fun onLongClick(model: TaskNet)
    }
}