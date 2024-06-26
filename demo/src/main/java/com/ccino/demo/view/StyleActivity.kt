package com.ccino.demo.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.ccino.demo.databinding.ActivityStyleBinding

private const val TAG = "StyleActivity"

/**
 * 为每个 view 添加额外的 attrs
 */
class StyleActivity : ComponentActivity() {
    private lateinit var binding: ActivityStyleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bold3.paint.isFakeBoldText = true
        binding.bold4.setTypeface(Typeface.DEFAULT_BOLD)
    }
}
