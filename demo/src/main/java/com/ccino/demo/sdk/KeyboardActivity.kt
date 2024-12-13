package com.ccino.demo.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityKeyboardBinding

class KeyboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeyboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.content, KeyboardFragment())
        }.commitNow()
    }
}