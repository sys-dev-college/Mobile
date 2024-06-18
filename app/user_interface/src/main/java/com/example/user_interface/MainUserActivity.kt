package com.example.user_interface

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.user_interface.databinding.ActivityMainUserBinding

class MainUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaseFragment(UserCalendar())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.ic_calendar -> replaseFragment(UserCalendar())
                R.id.ic_chat -> replaseFragment(UserChat())
                R.id.ic_profile -> replaseFragment(UserProfile())

                else -> {


                }


            }
            true

        }

        }

    private fun replaseFragment (fragment: Fragment){
            val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_rep, fragment)
        fragmentTransaction.addToBackStack(this.javaClass.name)
        fragmentTransaction.commit()
    }
}
