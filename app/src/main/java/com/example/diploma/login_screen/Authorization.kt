package com.example.diploma.login_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.diploma.MainActivity
import com.example.diploma.MainActivity.Companion.USER_EMAIL
import com.example.diploma.MainActivity.Companion.USER_ID
import com.example.diploma.MainActivity.Companion.USER_TOKEN
import com.example.diploma.R
import com.example.diploma.databinding.FragmentAuthorizationBinding
import com.example.diploma.user_screen.UserScreenFragment
import com.example.diploma.user_screen.model.UserCredResponse
import com.example.diploma.user_screen.model.me.MeResponse
import com.example.diploma.user_screen.retrofit.RetrofitClient

class Authorization : Fragment() {

    companion object {
        private const val DEBOUNCE = 300L
        private const val EMAIL_REGEX = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*\$"
        private const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$"
    }

    private lateinit var binding: FragmentAuthorizationBinding

    private val nameMLD = MutableLiveData("")
    private val passwordMLD = MutableLiveData("")
    private val isPasswordMLD = MutableLiveData(false)
    private val isEmailMLD = MutableLiveData(false)
    private val isButtonActiveMLD = MutableLiveData(false)

    private val onResponse = MutableLiveData<UserCredResponse?>()
    private val onMeResponse = MutableLiveData<MeResponse?>()

    private val handler = Handler(Looper.getMainLooper())
    private val prefs: SharedPreferences
        get() = requireActivity().getPreferences(Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).makeNavigationGone()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            RetrofitClient.loginUser(
                email = nameMLD.value!!,
                password = passwordMLD.value!!,
                onResponse
            )
        }
        binding.fragmentAuthorizationEmail.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onEmailChange(it.toString())
                },
                DEBOUNCE
            )
        }
        binding.fragmentAuthorizationPassword.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onPasswordChange(it.toString())
                },
                DEBOUNCE
            )
        }

        isButtonActiveMLD.observe(viewLifecycleOwner) {
            binding.btnContinue.isEnabled = it
        }
        isEmailMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it && isPasswordMLD.value ?: false
        }
        isPasswordMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it && isEmailMLD.value ?: false
        }

        onResponse.observe(viewLifecycleOwner) {
            it?.let {
                if (it.accessToken.isNotBlank()) {
                    prefs.edit().putString(USER_TOKEN, it.accessToken).apply()
                    RetrofitClient.getMe(it.accessToken, onMeResponse)
                }
            }
        }
        onMeResponse.observe(viewLifecycleOwner) {
            it?.let {
                prefs.edit().putString(USER_ID, it.id).apply()
                prefs.edit().putString(USER_EMAIL, it.email).apply()
                navigateToUserScreen()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onResponse.removeObservers(viewLifecycleOwner)
        onResponse.value = null
        onMeResponse.removeObservers(viewLifecycleOwner)
        onMeResponse.value = null
    }

    private fun onEmailChange(text: String) {
        val hasError = text.matches(EMAIL_REGEX.toRegex())
        isEmailMLD.value = hasError
        binding.fragmentAuthorizationEmail.error = when (hasError) {
            false -> resources.getString(R.string.fragment_authorization_name_error)
            true -> null
        }
        nameMLD.value = text
    }

    private fun onPasswordChange(text: String) {
        val hasError = text.matches(PASSWORD_REGEX.toRegex())
        isPasswordMLD.value = hasError
        binding.fragmentAuthorizationPassword.error = when (hasError) {
            false -> resources.getString(R.string.fragment_authorization_password_error)
            true -> null
        }
        passwordMLD.value = text
    }

    private fun navigateToUserScreen() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, UserScreenFragment())
        transaction.addToBackStack(this.javaClass.name)
        transaction.commit()
    }
}