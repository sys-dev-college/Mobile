package com.example.diploma.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.SplashScreenBinding


class SplashFragment : BaseFragment() {

    private lateinit var binding: SplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashScreenBinding.inflate(inflater, container, false)
        return binding.root

    }
}