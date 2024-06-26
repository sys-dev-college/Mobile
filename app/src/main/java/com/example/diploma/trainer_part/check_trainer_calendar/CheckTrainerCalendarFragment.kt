package com.example.diploma.trainer_part.check_trainer_calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentCheckTrainerCalendarBinding

class CheckTrainerCalendarFragment : BaseFragment() {

    private lateinit var binding: FragmentCheckTrainerCalendarBinding
    lateinit var adapter: CheckTrainerCalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckTrainerCalendarBinding.inflate(inflater, container, false)
        val items = listOf("10:00 Анжела Рудольфовна", "12:00 Александр Боюшенко",
            "13:00 Вероника Ануфриева", "14:00 Вася Петя", "15:30 Андрей Петров")
        adapter = CheckTrainerCalendarAdapter(items)
        (requireActivity() as MainActivity).makeNavigationVisible()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fragmentCheckTrainerCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Формирование данных на основе выбранной даты (пример)
            val selectedDateItems = listOf(
                "Выбранная дата: $dayOfMonth/${month + 1}/$year - Элемент 1",
                "Выбранная дата: $dayOfMonth/${month + 1}/$year - Элемент 2",
                "Выбранная дата: $dayOfMonth/${month + 1}/$year - Элемент 3"
            )
            adapter.updateItems(selectedDateItems)
        }
    }
}