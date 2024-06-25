package com.example.diploma.trainer_part.user_change

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.diploma.MainActivity
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentClientsDetailBinding
import com.example.diploma.trainer_part.user_change.model.UserData
import com.example.diploma.trainer_part.users_calendar.CheckUserCalendar
import com.example.diploma.trainer_part.users_calendar.CheckUserCalendar.Companion.USER_ID

class UserSetChanges : BaseFragment() {
    companion object {
        const val USER_DATA = "USER_DATA"
    }

    private lateinit var binding: FragmentClientsDetailBinding
    private var args: UserData?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientsDetailBinding.inflate(inflater, container, false)
        args = arguments?.getSerializable(USER_DATA) as UserData?
        (requireActivity() as MainActivity).makeNavigationVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentClientsDetailBtnChat.setOnClickListener{
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse(args?.telegramUrl))
            startActivity(telegram)
        }

        binding.fragmentClientsDetailBtnCalendar.setOnClickListener {
            val fragment = CheckUserCalendar()
            fragment.arguments = bundleOf(Pair(USER_ID, args?.id.orEmpty()))
            navigateTo(fragment)
        }
    }
}