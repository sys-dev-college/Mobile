package com.example.diploma

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.diploma.databinding.ActivityMainBinding
import com.example.diploma.splash_screen.SplashFragment
import com.example.diploma.start_screen.ui.MainStartFragment
import com.example.diploma.user_chat.FragmentUserChat
import com.example.diploma.user_profile.FragmentUserProfile
import com.example.diploma.user_screen.UserScreenFragment
import com.example.diploma.user_screen.model.me.RoleName

class MainActivity : AppCompatActivity() {

    companion object {
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_ROLE = "USER_ROLE"
        const val TELEGRAM_ID = "TELEGRAM_ID"
        const val TASK_ID = "TASK_ID"
        const val TASK_DATE = "TASK_DATE"
    }

    private lateinit var prefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = this.getPreferences(Context.MODE_PRIVATE)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, SplashFragment())
                .commit()
            Handler(Looper.getMainLooper()).postDelayed({
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, MainStartFragment())
                    .commit()
            }, 5000L)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            val userRole = RoleName.byName(prefs.getString(USER_ROLE, "") ?: "")
            when (it.itemId) {
                R.id.user_calendar -> replaceUserFragment(choseCalendar(userRole))
                R.id.user_chat -> replaceUserFragment(FragmentUserChat())
                else -> replaceUserFragment(FragmentUserProfile())
            }
            true
        }
        makeNavigationGone()
        window.statusBarColor = Color.parseColor("#C8C7C7")
    }

    private fun choseCalendar(userRole: RoleName): Fragment =
        when (userRole) {
            RoleName.USER -> UserScreenFragment()
            RoleName.TRAINER -> UserScreenFragment()
        }

    fun makeNavigationVisible() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun makeNavigationGone() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun replaceUserFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment_container, fragment)
        fragmentTransaction.addToBackStack(this.javaClass.name)
        fragmentTransaction.commit()
    }
}