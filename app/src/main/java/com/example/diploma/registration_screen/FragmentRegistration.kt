package com.example.diploma.registration_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diploma.R
import com.example.diploma.databinding.FragmentRegistrationBinding
import com.example.diploma.login_screen.Authorization

class FragmentRegistration : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentRegistrationLoginButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, Authorization())
            transaction.addToBackStack(this.javaClass.name)
            transaction.commit()
        }
    }
}

