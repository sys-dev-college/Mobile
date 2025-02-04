package com.example.diploma.user_chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentUserChatBinding

class FragmentUserChat : BaseFragment() {

    private lateinit var binding: FragmentUserChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserChatBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentUserChatNotBtn.setOnClickListener {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
            startActivity(telegram)
        }
    }
}