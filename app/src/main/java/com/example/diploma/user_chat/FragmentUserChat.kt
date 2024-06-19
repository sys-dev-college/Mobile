package com.example.diploma.user_chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.MainActivity
import com.example.diploma.databinding.FragmentUserChatBinding
import com.example.diploma.user_screen.model.UserFullResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient

class FragmentUserChat : Fragment() {

    private lateinit var binding: FragmentUserChatBinding
    private val onResponse = MutableLiveData(UserFullResponse("", "", "", ""))
    private val prefs: SharedPreferences
        get() = requireActivity().getPreferences(Context.MODE_PRIVATE)

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
            val token = prefs.getString(MainActivity.USER_TOKEN, "") ?: ""
            val id = prefs.getString(MainActivity.USER_ID, "") ?: ""
            retrieveUser(token, id)
        }
        onResponse.observe(viewLifecycleOwner){
            it?.let {
                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/SandrSX"/*onResponse.value!!.telegramUrl*/))
//                telegram.setPackage("org.telegram.messenger")
                startActivity(telegram)
            }
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