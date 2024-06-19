package com.example.diploma.user_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.MainActivity.Companion.USER_ID
import com.example.diploma.MainActivity.Companion.USER_TOKEN
import com.example.diploma.databinding.FragmentUserScreenBinding
import com.example.diploma.user_screen.adapter.TasksAdapter
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import java.util.Date


class UserScreenFragment : Fragment() {

    private lateinit var binding: FragmentUserScreenBinding
    private val onResponse = MutableLiveData<List<TaskListResponse>>()
    private lateinit var adapter: TasksAdapter
    private val prefs: SharedPreferences
        get() = requireActivity().getPreferences(Context.MODE_PRIVATE)

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
            it?.let { response ->
                if (response.isNotEmpty()) {
                    adapter.items = response
                }
            }
        }

        val date = Date()
        binding.fragmentUserScreenCalendar.date = date.time
        binding.fragmentUserScreenCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            getTasks(
                year = year,
                month = month + 1,
                dayOfMonth = dayOfMonth
            )
        }
    }

    private fun getTasks(year: Int, month: Int, dayOfMonth: Int) {
        val token = prefs.getString(USER_TOKEN, "") ?: ""
        val id = prefs.getString(USER_ID, "") ?: ""

        val monthString = when (month.toString().length == 1) {
            true -> "0$month"
            false -> "$month"
        }

        RetrofitClient.getTasks(
            date = String.format("%s-%s-%s", year, monthString, dayOfMonth),
            userToken = token,
            userId = id,
            onResponse = onResponse
        )
    }
}