package com.example.diploma.task_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentTaskScreenBinding
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.model.TaskNet
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskScreenFragment : BaseFragment(), CoroutineScope, TaskScreenAdapter.OnCheckBoxClick {

    private companion object {
        const val PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    }

    private lateinit var binding: FragmentTaskScreenBinding
    private lateinit var adapter: TaskScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskScreenBinding.inflate(inflater, container, false)
        adapter = TaskScreenAdapter()
        adapter.onItemClick = this
        binding.fragmentTaskScreenRv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            val tasks = RetrofitClient.getTasks(
                date = date,
                userToken = userToken,
                userId = id
            )
            onResponse(tasks)
        }
    }

    override fun invoke(item: TaskNet) {
        launch {
            RetrofitClient.setTaskComplete(
                userKey = userToken,
                taskId = item.id,
                isComplete = item.completed
            )
        }
    }

    private fun onResponse(tasks: List<TaskListResponse>) {
        if (tasks.isNotEmpty()) {
            val item = tasks.find { model ->
                model.id == taskId
            }
            binding.fragmentTaskViewNameValue.text = item?.title
            binding.fragmentTaskViewTimeValue.text = makeTime(item?.scheduled)
            item?.let {
                adapter.items = item.tasks
            }
        }
    }

    private fun makeTime(timeString: String?): String {
        val sdf = SimpleDateFormat(PATTERN, Locale.getDefault())
        val date =
            try {
                timeString?.let { dateString ->
                    sdf.parse(dateString)
                }
            } catch (e: ParseException) {
                Date()
            }
        return String.format("%s:%s", date?.hours, date?.minutes)
    }
}