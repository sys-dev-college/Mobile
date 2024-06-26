package com.example.diploma.trainer_part.check_trainer_calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiCheckTrainerCalendarBinding

class CheckTrainerCalendarAdapter (private var itemList: List<String>): RecyclerView.Adapter<CheckTrainerCalendarAdapter.CheckTrainerCalendarViewHolder>() {

        class CheckTrainerCalendarViewHolder(val binding: LiCheckTrainerCalendarBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(text: String) {
            binding.liCheckTrainerCalendarText.text = text
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckTrainerCalendarAdapter.CheckTrainerCalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiCheckTrainerCalendarBinding.inflate(inflater, parent, false)
        return CheckTrainerCalendarAdapter.CheckTrainerCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckTrainerCalendarViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItems(newItems: List<String>) {
        itemList = newItems
        notifyDataSetChanged()
    }

}