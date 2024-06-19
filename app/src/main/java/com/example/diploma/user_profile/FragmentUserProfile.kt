package com.example.diploma.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.R
import com.example.diploma.databinding.FragmentUserProfileBinding
import com.example.diploma.start_screen.ui.MainStartFragment
import com.example.diploma.user_screen.model.UserResetResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient

class FragmentUserProfile : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val onResponse = MutableLiveData(UserResetResponse(false))

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
            resetUser("")
        }

        binding.fragmentUserProfileBtnAbout.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, FragmentAbout())
            transaction.addToBackStack(this.javaClass.name)
                .commit()
        }

        binding.fragmentUserProfileBtnLogout.setOnClickListener{
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, MainStartFragment())
            transaction.addToBackStack(this.javaClass.name)
                .commit()
        }
    }


    private fun resetUser(email: String) {
        RetrofitClient.resetUser(
            email = email,
            onResponse = onResponse
        )
    }
}