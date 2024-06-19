package com.example.diploma

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.diploma.splash_screen.SplashFragment
import com.example.diploma.start_screen.ui.MainStartFragment
import com.example.diploma.user_chat.FragmentUserChat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    private fun replaceUserFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, FragmentUserChat())
            .commit()
    }
}