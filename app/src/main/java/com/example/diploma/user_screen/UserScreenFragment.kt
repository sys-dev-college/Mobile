package com.example.diploma.user_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentUserScreenBinding
import com.example.diploma.task_screen.TaskScreenFragment
import com.example.diploma.user_screen.adapter.TasksAdapter
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch


class UserScreenFragment : BaseFragment(), TasksAdapter.OnClickListener {

    private lateinit var binding: FragmentUserScreenBinding
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserScreenBinding.inflate(inflater, container, false)
        adapter = TasksAdapter()
        adapter.listener = this
        adapter.items = emptyList()
        binding.fragmentUserScreenRv.adapter = adapter
        (requireActivity() as MainActivity).makeNavigationVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveDate = calendarDate
        if (saveDate != -1L) {
            binding.fragmentUserScreenCalendar.date = saveDate
            removeCalendarDate()
        }
        binding.fragmentUserScreenCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            launch {
                adapter.items = emptyList()
                adapter.items = getTasks(
                    year = year,
                    month = month + 1,
                    dayOfMonth = dayOfMonth
                )
            }
        }
    }

    private suspend fun getTasks(year: Int, month: Int, dayOfMonth: Int): List<TaskListResponse> {

        val monthString = when (month.toString().length == 1) {
            true -> "0$month"
            false -> "$month"
        }
        val date = String.format("%s-%s-%s", year, monthString, dayOfMonth)
        saveTaskDate(date)

        return RetrofitClient.getTasks(
            date = date,
            userToken = userToken,
            userId = id
        )
    }

    override fun onClick(model: TaskListResponse) {
        saveTaskId(model.id)
        saveCalendarDate(binding.fragmentUserScreenCalendar.date)
        navigateTo(TaskScreenFragment())
    }
}