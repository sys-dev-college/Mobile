package com.example.diploma.trainer_part.users_calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentChangeUserCalendarBinding
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class CheckUserCalendar : BaseFragment(), UserCalendarAdapter.OnClickListener {

    companion object {
        const val USER_ID = "USER_ID"
    }
    private lateinit var binding: FragmentChangeUserCalendarBinding
    private lateinit var adapter: UserCalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeUserCalendarBinding.inflate(inflater, container, false)
        adapter = UserCalendarAdapter()
        adapter.listener = this
        adapter.items = emptyList()
        binding.fragmentChangeUserCalendarRv.adapter = adapter
        (requireActivity() as MainActivity).makeNavigationVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveDate = calendarDate
        if (saveDate != -1L) {
            binding.fragmentChangeUserCalendarCalendar.date = saveDate
            removeCalendarDate()
        }
        binding.fragmentChangeUserCalendarCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            launch {
                adapter.items = emptyList()
                getTasks(
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

        val userId = arguments?.getString(USER_ID).orEmpty()

        return RetrofitClient.getTasks(
            date = date,
            userToken = userToken,
            userId = userId
        )
    }

    override fun onClick(model: TaskListResponse) {
        saveTaskId(model.id)
        saveCalendarDate(binding.fragmentChangeUserCalendarCalendar.date)
        navigateTo(CheckUserDetailTasks())
    }
}