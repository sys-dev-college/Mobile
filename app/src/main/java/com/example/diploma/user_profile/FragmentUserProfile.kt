package com.example.diploma.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.R
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentUserProfileBinding
import com.example.diploma.start_screen.ui.MainStartFragment

class FragmentUserProfile : BaseFragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentUserProfileBtnReset.setOnClickListener {
            clearPrefs()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, MainStartFragment())
            transaction.commit()
        }

        binding.fragmentUserProfileBtnAbout.setOnClickListener {
            navigateTo(FragmentAbout())
        }

        binding.fragmentUserProfileBtnLogout.setOnClickListener {
            clearPrefs()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, MainStartFragment())
            transaction.commit()
        }
    }
}