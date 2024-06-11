package com.ccino.demo.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityStyleBinding
import com.ccino.demo.widget.BlurDrawable

private const val TAG = "StyleActivity"

/**
 * 为每个 view 添加额外的 attrs
 */
class StyleActivity : ComponentActivity() {
    private lateinit var binding: ActivityStyleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.blurView.background = BlurDrawable(-0x80000000,20f)
    }
}
