package com.example.diploma.trainer_part.create_task

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.diploma.databinding.FragmentAddTaskBinding
import com.example.diploma.user_screen.retrofit.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateTaskFragment : BottomSheetDialogFragment() {

    companion object {
        const val USER_TOKEN = "USER_TOKEN"
        const val CLIENT_ID = "CLIENT_ID"
        const val CARD_DATE = "CARD_DATE"
        const val SELECTED_DATE = "SELECTED_DATE"

        private const val PATTERN = "HH:mm"
        private const val PATTERN_NET = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    }


    private lateinit var binding: FragmentAddTaskBinding
    private var cardDate: String = ""
    private var userToken: String = ""
    private var clientId: String = ""
    private var scheduled: String = ""
    private var title: String = ""
    private var selectedDate = Date()
    private var type: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        clientId = arguments?.getString(CLIENT_ID).orEmpty()
        userToken = arguments?.getString(USER_TOKEN).orEmpty()
        cardDate = arguments?.getString(CARD_DATE).orEmpty()
        selectedDate = Date(arguments?.getLong(SELECTED_DATE) ?: 0L)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentAddTaskEtName.addTextChangedListener {
            title = it.toString()
        }

        binding.fragmentAddTaskEtTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    scheduled = makeTime(hourOfDay, minute, calendar)
                    binding.fragmentAddTaskEtTime.text = String.format("%s:%s", hourOfDay, minute)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true,
            ).show()
        }

        binding.fragmentAddTaskCheckboxText.setOnClickListener {
            binding.fragmentAddTaskCheckbox.isChecked = !binding.fragmentAddTaskCheckbox.isChecked
            type = if (binding.fragmentAddTaskCheckbox.isChecked) {
                1
            } else {
                0
            }
        }

        binding.fragmentAddTaskCheckbox.setOnClickListener {
            binding.fragmentAddTaskCheckbox.isChecked = !binding.fragmentAddTaskCheckbox.isChecked
            type = if (binding.fragmentAddTaskCheckbox.isChecked) {
                1
            } else {
                0
            }
        }

        binding.fragmentAddTaskBtnCancel.setOnClickListener {
            dismiss()
        }

        binding.fragmentAddTaskBtnAccept.setOnClickListener {
            RetrofitClient.createCalendar(
                userToken = userToken,
                clientId = clientId,
                scheduled = scheduled,
                title = title,
                type = type,
            )
            binding.fragmentAddTaskProgress.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({}, 1000L)
            binding.fragmentAddTaskProgress.visibility = View.GONE
            dismiss()
        }
    }

    private fun makeTime(hour: Int, minute: Int, calendar: Calendar): String {
        val sdf = SimpleDateFormat(PATTERN, Locale.getDefault())
        val hourString = when (hour.toString().length == 1) {
            true -> "0$hour"
            false -> "$hour"
        }
        val minuteString = when (minute.toString().length == 1) {
            true -> "0$minute"
            false -> "$minute"
        }

        val timeString = String.format("%s:%s", hourString, minuteString)
        val date =
            try {
                sdf.parse(timeString)
            } catch (e: ParseException) {
                Date()
            }
        selectedDate.hours = hour
        selectedDate.minutes = minute
        selectedDate.month -= 1
        val newSdf = SimpleDateFormat(PATTERN_NET, Locale.getDefault())
        return newSdf.format(selectedDate)
    }
}