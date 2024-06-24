package com.ccino.demo.sdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.ccino.demo.databinding.ActivitySecondBinding


class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dropTextView = binding.btn
        val dragData = intent.getStringExtra("dragData")
        dropTextView.text = dragData
    }
}