package com.example.diploma.trainer_part.check_trainer_calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.databinding.LiCheckTrainerCalendarBinding

class CheckTrainerCalendarAdapter :
    RecyclerView.Adapter<CheckTrainerCalendarAdapter.CheckTrainerCalendarViewHolder>() {

    var items: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckTrainerCalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LiCheckTrainerCalendarBinding.inflate(inflater, parent, false)
        return CheckTrainerCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckTrainerCalendarViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            liCheckTrainerCalendarText.text = item
        }
    }

    class CheckTrainerCalendarViewHolder(val binding: LiCheckTrainerCalendarBinding) :
        RecyclerView.ViewHolder(binding.root)
}