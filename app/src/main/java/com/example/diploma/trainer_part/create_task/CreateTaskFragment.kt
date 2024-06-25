package com.example.diploma.trainer_part.create_task

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

class CreateTaskFragment : BottomSheetDialogFragment() {

    companion object {
        const val USER_TOKEN = "USER_TOKEN"
        const val CLIENT_ID = "CLIENT_ID"
    }


    private lateinit var binding: FragmentAddTaskBinding
    private var userToken: String = ""
    private var clientId: String = ""
    private var scheduled: String = ""
    private var title: String = ""
    private var type: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        clientId = arguments?.getString(CLIENT_ID).orEmpty()
        userToken = arguments?.getString(USER_TOKEN).orEmpty()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentAddTaskEtName.addTextChangedListener {
            title = it.toString()
        }

        binding.fragmentAddTaskEtTime.addTextChangedListener {
            scheduled = it.toString()
            val calendar = Calendar.getInstance()
            /*val fragment = TimePickerDialog(
                context = requireContext(),
                listener = object:TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        TODO("Not yet implemented")
                    }

                },
                hourOfDay = calendar.get(Calendar.HOUR_OF_DAY),
                is24HourView = true,
                minutes = calendar.get(Calendar.MINUTE),
            )*/
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

}