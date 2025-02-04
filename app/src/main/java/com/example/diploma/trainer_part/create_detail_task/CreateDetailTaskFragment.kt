package com.example.diploma.trainer_part.create_detail_task

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.diploma.databinding.FragmentAddDetailTasksBinding
import com.example.diploma.user_screen.retrofit.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateDetailTaskFragment : BottomSheetDialogFragment() {
    companion object{
        const val USER_TOKEN = "USER_TOKEN"
        const val TASK_ID = "TASK_ID"
    }

    private lateinit var binding: FragmentAddDetailTasksBinding
    private var calendarId: String = ""
    private var userToken: String = ""
    private var name: String = ""
    private var amount: Int = 0
    private var unit: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDetailTasksBinding.inflate(inflater, container, false)
        calendarId = arguments?.getString(TASK_ID).orEmpty()
        userToken = arguments?.getString(USER_TOKEN).orEmpty()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentAddDetailTasksEtName.addTextChangedListener {
            name = it.toString()
        }

        binding.fragmentAddDetailTasksEtAmount.addTextChangedListener {
            amount = it.toString().toInt()
        }

        binding.fragmentAddDetailTasksEtUnit.addTextChangedListener {
            unit = it.toString()
        }

        binding.fragmentAddDetailTasksBtnCancel.setOnClickListener {
            dismiss()
        }

        binding.fragmentAddDetailTasksBtnAccept.setOnClickListener {
            RetrofitClient.createTask(
                userToken = userToken,
                calendarId = calendarId,
                name = name,
                amount = amount,
                unit = unit
            )
            binding.fragmentAddDetailTasksProgress.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({}, 1000L)
            binding.fragmentAddDetailTasksProgress.visibility = View.GONE
            dismiss()
        }
    }
}