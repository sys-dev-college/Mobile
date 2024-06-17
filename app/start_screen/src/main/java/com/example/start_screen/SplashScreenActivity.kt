package com.example.start_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        mHandler = Handler()
        mRunnable = Runnable {
            // Start the next activity
//            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
            finish()
        }
        mHandler.postDelayed(mRunnable, 3000) // delay for 3 seconds
    }
}