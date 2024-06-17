package com.example.user_interface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.user_interface.databinding.FragmentDetailCardBinding
import com.example.user_interface.models.Task
import com.example.user_interface.viewmodels.CardsViewModel

class DetailFragment : Fragment() {
    private lateinit var viewModel: CardsViewModel

    //    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentDetailCardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CardsViewModel::class.java)

//        val view = inflater.inflate(R.layout.fragment_detail_card, container, false)
        binding = FragmentDetailCardBinding.inflate(inflater, container, false)
//        recyclerView = view.findViewById(R.id.fragment_detail_card_rv)

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            binding.fragmentDetailCardRv.adapter = TasksAdapter(tasks ?: emptyList())
        }

        return binding.root
    }

    inner class TasksAdapter(private val tasks: List<Task>) :
        RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(tasks[position])
        }

        override fun getItemCount(): Int = tasks.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(task: Task) {
                itemView.findViewById<TextView>(R.id.name_textview).text = task.name
                itemView.findViewById<TextView>(R.id.amount_textview).text = task.amount

                itemView.findViewById<CheckBox>(R.id.checkbox)
                    .setOnCheckedChangeListener { _, isChecked ->
                        // Update task as finished or not
                    }
            }
        }
    }
}