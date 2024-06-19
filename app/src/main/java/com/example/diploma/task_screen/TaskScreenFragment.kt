package com.example.diploma.task_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.MainActivity.Companion.TASK_DATE
import com.example.diploma.MainActivity.Companion.TASK_ID
import com.example.diploma.MainActivity.Companion.USER_ID
import com.example.diploma.MainActivity.Companion.USER_TOKEN
import com.example.diploma.databinding.FragmentTaskScreenBinding
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskScreenFragment : Fragment() {

    private lateinit var binding: FragmentTaskScreenBinding
    private lateinit var adapter: TaskScreenAdapter
    private val prefs: SharedPreferences
        get() = requireActivity().getPreferences(Context.MODE_PRIVATE)
    private val onResponse = MutableLiveData<List<TaskListResponse>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskScreenBinding.inflate(inflater, container, false)
        adapter = TaskScreenAdapter()
        binding.fragmentTaskScreenRv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onResponse.observe(viewLifecycleOwner) {
            it?.let { response ->
                if (response.isNotEmpty()) {
                    val id = prefs.getString(TASK_ID, "") ?: ""
                    val item = response.find { model ->
                        model.id == id
                    }
                    binding.fragmentTaskViewNameValue.text = item?.title
                    val dateTimeString = item?.scheduled?.substring(0, 23)  // Обрезаем строку до миллисекунд
// Определяем формат входной строки
                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
// Определяем формат выходной строки (только часы и минуты)
                    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
// Преобразуем строку в LocalDateTime объект
                    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)
// Преобразуем LocalDateTime объект обратно в строку в нужном формате
                    val timeString = dateTime.format(outputFormatter)
                    binding.fragmentTaskViewTimeValue.text = timeString
                    item?.let {
                        adapter.items = item.tasks
                    }
                }
            }
        }
        val date = prefs.getString(TASK_DATE, "") ?: ""
        val token = prefs.getString(USER_TOKEN, "") ?: ""
        val id = prefs.getString(USER_ID, "") ?: ""
        RetrofitClient.getTasks(
            date = date,
            userToken = token,
            userId = id,
            onResponse = onResponse
        )
    }
}