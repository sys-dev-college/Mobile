package com.example.diploma.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diploma.databinding.AboutBinding

class FragmentAbout : Fragment() {

    private lateinit var binding: AboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutBinding.inflate(inflater, container, false)
        return binding.root
    }
}