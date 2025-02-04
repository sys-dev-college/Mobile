package com.example.diploma.trainer_part.users_calendar

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentChangeUserCalendarBinding
import com.example.diploma.trainer_part.create_task.CreateTaskFragment
import com.example.diploma.trainer_part.create_task.CreateTaskFragment.Companion.CLIENT_ID
import com.example.diploma.trainer_part.create_task.CreateTaskFragment.Companion.SELECTED_DATE
import com.example.diploma.trainer_part.create_task.CreateTaskFragment.Companion.USER_TOKEN
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks.Companion.CARD_NAME
import com.example.diploma.trainer_part.users_detail_task.CheckUserDetailTasks.Companion.CARD_TIME
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import java.util.Date

class CheckUserCalendar : BaseFragment(), UserCalendarAdapter.OnClickListener,
    UserCalendarAdapter.OnLongClickListener {

    companion object {
        const val USER_ID = "USER_ID"
    }

    private lateinit var binding: FragmentChangeUserCalendarBinding
    private lateinit var adapter: UserCalendarAdapter
    private lateinit var clientId: String

    private var selectedDate = Date()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeUserCalendarBinding.inflate(inflater, container, false)
        adapter = UserCalendarAdapter()
        adapter.listener = this
        adapter.longClickListener = this
        adapter.items = emptyList()
        binding.fragmentChangeUserCalendarRv.adapter = adapter
        clientId = arguments?.getString(USER_ID).orEmpty()
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
                adapter.items = getTasks(
                    year = year,
                    month = month + 1,
                    dayOfMonth = dayOfMonth
                )
            }
        }
        binding.fragmentChangeUserCalendarBtnPlus.setOnClickListener {
            val fragment = CreateTaskFragment()
            fragment.arguments = bundleOf(
                Pair(USER_TOKEN, userToken),
                Pair(CLIENT_ID, clientId),
                Pair(SELECTED_DATE, selectedDate.time)
            )
            fragment.show(parentFragmentManager, "tag")
        }
    }

    private suspend fun getTasks(year: Int, month: Int, dayOfMonth: Int): List<TaskListResponse> {

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(year, month, dayOfMonth)
        selectedDate = calendar.time

        val monthString = when (month.toString().length == 1) {
            true -> "0$month"
            false -> "$month"
        }
        val date = String.format("%s-%s-%s", year, monthString, dayOfMonth)
        saveTaskDate(date)

        return RetrofitClient.getTasks(
            date = date,
            userToken = userToken,
            userId = clientId
        )
    }

    override fun onClick(model: TaskListResponse) {
        saveTaskId(model.id)
        saveCalendarDate(binding.fragmentChangeUserCalendarCalendar.date)
        val fragment = CheckUserDetailTasks()
        val name = model.title
        val time = model.scheduled
        fragment.arguments = bundleOf(Pair(CARD_NAME, name), Pair(CARD_TIME, time))
        /*fragment.show(parentFragmentManager, "tag")*/
        navigateTo(fragment)
    }

    override fun onLongClick(model: TaskListResponse) {
        launch {
            RetrofitClient.deleteCalendar(
                userKey = userToken,
                calendarId = model.id
            )
        }
    }

}