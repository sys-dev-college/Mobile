package com.example.diploma.trainer_part.check_trainer_calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentCheckTrainerCalendarBinding

class CheckTrainerCalendarFragment : BaseFragment() {

    private lateinit var binding: FragmentCheckTrainerCalendarBinding
    private lateinit var adapter: CheckTrainerCalendarAdapter

    private val items = listOf(
        "10:00 Анжела Рудольфовна",
        "12:00 Александр Боюшенко",
        "13:00 Вероника Ануфриева",
        "14:00 Вася Петя",
        "15:30 Андрей Петров"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckTrainerCalendarBinding.inflate(inflater, container, false)
        adapter = CheckTrainerCalendarAdapter()
        binding.fragmentCheckTrainerCalendarRv.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentCheckTrainerCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            adapter.items = items
        }
    }
}