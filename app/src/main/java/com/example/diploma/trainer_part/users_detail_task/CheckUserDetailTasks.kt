package com.example.diploma.trainer_part.users_detail_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentChangeUserTasksBinding
import com.example.diploma.trainer_part.create_detail_task.CreateDetailTaskFragment
import com.example.diploma.trainer_part.create_detail_task.CreateDetailTaskFragment.Companion.TASK_ID
import com.example.diploma.trainer_part.create_detail_task.CreateDetailTaskFragment.Companion.USER_TOKEN
import com.example.diploma.user_screen.model.TaskNet
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckUserDetailTasks : BaseFragment(), UserDetailTasksAdapter.OnCheckBoxClick, UserDetailTasksAdapter.OnLongClickListener {

    companion object {
        const val PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
        const val CARD_NAME = "CARD_NAME"
        const val CARD_TIME = "CARD_TIME"
    }

    private lateinit var binding: FragmentChangeUserTasksBinding
    private lateinit var adapter: UserDetailTasksAdapter
    private lateinit var cardName: String
    private lateinit var cardTime: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeUserTasksBinding.inflate(inflater, container, false)
        adapter = UserDetailTasksAdapter()
        adapter.onItemClick = this
        adapter.longClickListener = this
        cardName = arguments?.getString(CARD_NAME).orEmpty()
        cardTime = arguments?.getString(CARD_TIME).orEmpty()
        binding.fragmentChangeUserTasksRv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            val tasks = RetrofitClient.getTasksByCalendar(
                userToken = userToken,
                calendarId = taskId,
            )
            onResponse(tasks)
        }

        binding.fragmentChangeUserTasksBtnPlus.setOnClickListener{
            val fragment = CreateDetailTaskFragment()
            fragment.arguments = bundleOf(Pair(USER_TOKEN, userToken), Pair(TASK_ID, taskId))
            fragment.show(parentFragmentManager, "tag")
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

    private fun onResponse(tasks: List<TaskNet>) {
        adapter.items = tasks

        binding.fragmentChangeUserTasksViewNameValue.text = cardName
        binding.fragmentChangeUserTasksViewTimeValue.text = makeTime(cardTime)

    }

    private fun makeTime(timeString: String?): String {
        val sdf = SimpleDateFormat(CheckUserDetailTasks.PATTERN, Locale.getDefault())
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

    override fun onLongClick(model: TaskNet) {
        launch {
            RetrofitClient.deleteTask(
                userKey = userToken,
                taskId = model.id
            )
        }
    }
}