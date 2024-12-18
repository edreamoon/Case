package com.ccino.demo.sdk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityKeyboardBinding

/**
 * 1. 非沉浸式状态栏时，
 *  - 有可滑动的view时(scrollview/listView)并不需要去适配，键盘弹出时，会自动将布局上移，不会被键盘遮挡 （注释掉 enableEdgeToEdge）
 *  - 无可滑动的view时，需要适配，添加 windowSoftInputMode="adjustResize" 即可
 * 2. 沉浸式状态栏，
 *  - 添加 windowSoftInputMode="adjustResize"，
 *  - 布局添加 android:fitsSystemWindows="true"
 *  - fitsSystemWindows view 需要去除顶部多余 padding， ViewCompat.setOnApplyWindowInsetsListener(rootView)
 */
class KeyboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeyboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.content, KeyboardFragment())
        }.commitNow()
    }
}