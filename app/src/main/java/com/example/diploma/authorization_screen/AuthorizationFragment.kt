package com.example.diploma.authorization_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.example.diploma.MainActivity
import com.example.diploma.R
import com.example.diploma.base.BaseFragment
import com.example.diploma.databinding.FragmentAuthorizationBinding
import com.example.diploma.trainer_part.users_list_screen.UsersListFragment
import com.example.diploma.user_screen.UserScreenFragment
import com.example.diploma.user_screen.model.UserCredResponse
import com.example.diploma.user_screen.model.me.RoleName
import com.example.diploma.user_screen.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class AuthorizationFragment : BaseFragment() {

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

    private val handler = Handler(Looper.getMainLooper())

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
            launch {
                val auth = RetrofitClient.loginUser(
                    email = nameMLD.value!!,
                    password = passwordMLD.value!!
                )
                onAuthorizationResponse(auth)
            }
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

    private fun onAuthorizationResponse(userCredResponse: UserCredResponse?) {
        userCredResponse?.let {
            saveToken(it.token?.accessToken)
            saveUserId(it.user?.id.orEmpty())
            saveUserEmail(it.user?.email.orEmpty())
            saveTelegramUrl(it.user?.telegramUrl.orEmpty())

            val role = RoleName.byName(it.role?.name.orEmpty())
            saveRole(role.name)

            navigateTo(
                when (role) {
                    RoleName.USER -> UserScreenFragment()
                    RoleName.TRAINER -> UsersListFragment()
                }
            )
        }
    }
}