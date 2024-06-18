package com.example.diploma.login_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diploma.R
import com.example.diploma.databinding.FragmentAuthorizationBinding
import com.example.diploma.user_screen.UserScreenFragment

class Authorization : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, UserScreenFragment())
            transaction.addToBackStack(this.javaClass.name)
            transaction.commit()
        }
    }
}