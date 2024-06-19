package com.example.diploma.user_chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.databinding.FragmentUserChatBinding
import com.example.diploma.user_screen.model.UserFullResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient

class FragmentUserChat : Fragment() {

    private lateinit var binding: FragmentUserChatBinding
    private val onResponse = MutableLiveData(UserFullResponse("", "", "", ""))

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
        retrieveUser("", "")
        binding.fragmentUserChatNotBtn.setOnClickListener {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse(onResponse.value!!.telegramUrl))
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)
        }
    }

    private fun retrieveUser(userKey: String, userId: String) {
        RetrofitClient.retrieveUser(
            userKey = userKey,
            userId = userId,
            onResponse = onResponse
        )
    }
}