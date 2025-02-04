package com.example.diploma.trainer_part.users_list_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiUsersListItemBinding
import com.example.diploma.user_screen.model.TrainerUser

class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {

    var onCLick: OnButtonClick? = null
    var items: List<TrainerUser> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiUsersListItemBinding.inflate(inflater, parent, false)
        return UsersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liUsersListItemBtn.text = item.name
            liUsersListItemBtn.setOnClickListener { onCLick?.invoke(item) }
        }
    }

    class UsersListViewHolder(val binding: LiUsersListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnButtonClick {
        fun invoke(item: TrainerUser)
    }
}