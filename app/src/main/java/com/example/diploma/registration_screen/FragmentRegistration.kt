package com.example.diploma.registration_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.example.diploma.R
import com.example.diploma.authorization_screen.AuthorizationFragment
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentRegistrationBinding
import com.example.diploma.user_screen.UserScreenFragment
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class FragmentRegistration : BaseFragment() {

    private companion object {
        private const val DEBOUNCE = 500L
        const val NAME_REGEX = "^([а-яА-Я]+)\\s([а-яА-Я]+)\$"
        const val EMAIL_REGEX = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*\$"
        const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}\$"
    }

    private lateinit var binding: FragmentRegistrationBinding

    private val nameMLD = MutableLiveData("")
    private val emailMLD = MutableLiveData("")
    private val passwordMLD = MutableLiveData("")
    private val telegramMLD = MutableLiveData("")

    private val isNameMLD = MutableLiveData(false)
    private val isEmailMLD = MutableLiveData(false)
    private val isPasswordMLD = MutableLiveData(false)
    private val isTelegramMLD = MutableLiveData(false)
    private val isButtonActiveMLD = MutableLiveData(false)

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            val names = nameMLD.value!!.split(" ")
            launch {
                val res = RetrofitClient.registerUser(
                    email = emailMLD.value!!,
                    password = passwordMLD.value!!,
                    firstName = names[0],
                    lastName = names[1],
                    telegramUrl = String.format("https://t.me/%s/", telegramMLD.value),
                )
                if (res?.status == true) {
                    navigateTo(UserScreenFragment())
                }
            }
        }

        binding.fragmentRegistrationLoginButton.setOnClickListener {
            navigateTo(AuthorizationFragment())
        }

        binding.name.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onNameChange(it.toString())
                },
                DEBOUNCE
            )
        }
        binding.email.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onEmailChange(it.toString())
                },
                DEBOUNCE
            )
        }
        binding.password.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onPasswordChange(it.toString())
                },
                DEBOUNCE
            )
        }
        binding.telegramId.addTextChangedListener {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(
                {
                    onTelegramChange(it.toString())
                },
                DEBOUNCE
            )
        }

        isButtonActiveMLD.observe(viewLifecycleOwner) {
            binding.btnContinue.isEnabled = it
        }

        isNameMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it
                    && isEmailMLD.value ?: false
                    && isPasswordMLD.value ?: false
                    && isTelegramMLD.value ?: false
        }

        isEmailMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it
                    && isNameMLD.value ?: false
                    && isPasswordMLD.value ?: false
                    && isTelegramMLD.value ?: false
        }

        isPasswordMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it
                    && isNameMLD.value ?: false
                    && isEmailMLD.value ?: false
                    && isTelegramMLD.value ?: false
        }

        isTelegramMLD.observe(viewLifecycleOwner) {
            isButtonActiveMLD.value = it
                    && isNameMLD.value ?: false
                    && isEmailMLD.value ?: false
                    && isPasswordMLD.value ?: false
        }
    }

    private fun onNameChange(text: String) {
        val hasError = text.matches(NAME_REGEX.toRegex())
        isNameMLD.value = hasError
        binding.name.error = when (hasError) {
            false -> resources.getString(R.string.fragment_registration_name_error)
            true -> null
        }
        nameMLD.value = text
    }

    private fun onEmailChange(text: String) {
        val hasError = text.matches(EMAIL_REGEX.toRegex())
        isEmailMLD.value = hasError
        binding.email.error = when (hasError) {
            false -> resources.getString(R.string.fragment_registration_email_error)
            true -> null
        }
        emailMLD.value = text
    }

    private fun onPasswordChange(text: String) {
        val hasError = text.matches(PASSWORD_REGEX.toRegex())
        isPasswordMLD.value = hasError
        binding.password.error = when (hasError) {
            false -> resources.getString(R.string.fragment_registration_password_error)
            true -> null
        }
        passwordMLD.value = text
    }

    private fun onTelegramChange(text: String) {
        isTelegramMLD.value = text.isNotBlank()
        binding.telegramId.error = when (text.isBlank()) {
            true -> resources.getString(R.string.fragment_registration_telegram_error)
            false -> null
        }
        telegramMLD.value = text
    }
}

