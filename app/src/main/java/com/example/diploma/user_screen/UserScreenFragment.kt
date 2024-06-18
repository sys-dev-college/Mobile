package com.example.diploma.user_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.databinding.FragmentUserScreenBinding
import com.example.diploma.user_screen.adapter.TasksAdapter
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import java.util.Date


class UserScreenFragment : Fragment() {

    private lateinit var binding: FragmentUserScreenBinding
    private val onResponse = MutableLiveData(TaskListResponse(emptyList()))
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserScreenBinding.inflate(inflater, container, false)
        adapter = TasksAdapter()
        binding.fragmentUserScreenRv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onResponse.observe(viewLifecycleOwner) {
            adapter.items = it.detail
        }

        val date = Date()
        binding.fragmentUserScreenCalendar.date = date.time
        binding.fragmentUserScreenCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            getTasks(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth
            )
        }
    }

    private fun getTasks(year: Int, month: Int, dayOfMonth: Int) {
        RetrofitClient.getTasks(
            baseUrl = "https://it-fits.ru",
            date = String.format("%s-%s-%s", year, month, dayOfMonth),
            onResponse = onResponse
        )
    }
}