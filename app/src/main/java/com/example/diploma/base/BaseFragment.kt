package com.example.diploma.base

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.example.diploma.MainActivity.Companion.TASK_DATE
import com.example.diploma.MainActivity.Companion.TASK_ID
import com.example.diploma.MainActivity.Companion.TELEGRAM_ID
import com.example.diploma.MainActivity.Companion.USER_EMAIL
import com.example.diploma.MainActivity.Companion.USER_ID
import com.example.diploma.MainActivity.Companion.USER_ROLE
import com.example.diploma.MainActivity.Companion.USER_TOKEN
import com.example.diploma.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

open class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    private val prefs: SharedPreferences
        get() = requireActivity().getPreferences(Context.MODE_PRIVATE)
    protected val date: String
        get() = prefs.getString(TASK_DATE, "") ?: ""
    protected val userToken: String
        get() = prefs.getString(USER_TOKEN, "") ?: ""
    protected val id: String
        get() = prefs.getString(USER_ID, "") ?: ""
    protected val taskId: String
        get() = prefs.getString(TASK_ID, "") ?: ""
    protected val telegramUrl: String
        get() = prefs.getString(TELEGRAM_ID, "") ?: ""
    protected val userRole: String
        get() = prefs.getString(USER_ROLE, "") ?: ""
    protected val calendarDate: Long
        get() = prefs.getLong("CALENDAR_DATE", -1)

    fun clearPrefs() {
        prefs.edit().clear().apply()
    }

    fun saveToken(token: String?) {
        prefs.edit().putString(USER_TOKEN, token.orEmpty()).apply()
    }

    fun saveUserId(id: String) {
        prefs.edit().putString(USER_ID, id).apply()
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(USER_EMAIL, email).apply()
    }

    fun saveTelegramUrl(id: String) {
        prefs.edit().putString(TELEGRAM_ID, id).apply()
    }

    fun saveTaskDate(date: String) {
        prefs.edit().putString(TASK_DATE, date).apply()
    }

    fun saveTaskId(id: String) {
        prefs.edit().putString(TASK_ID, id).apply()
    }

    fun saveRole(role: String) {
        prefs.edit().putString(USER_ROLE, role).apply()
    }

    fun saveCalendarDate(date: Long) {
        prefs.edit().putLong("CALENDAR_DATE", date).apply()
    }

    fun removeCalendarDate() {
        prefs.edit().remove("CALENDAR_DATE").apply()
    }

    fun navigateTo(fragment: BaseFragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack(fragment::class.java.name)
        transaction.commit()
    }
}