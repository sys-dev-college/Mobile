package com.example.start_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.start_screen.databinding.ActivityMainStartBinding

class MainStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main_start)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivityMainStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}