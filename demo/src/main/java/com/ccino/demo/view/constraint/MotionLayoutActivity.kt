package com.ccino.demo.view.constraint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityMotionLayoutBinding
import com.ccino.demo.view.MoreAdapter

private const val TAG = "MotionLayoutActivity"

/**
 * MotionLayout 仅适用于为其直接子级添加动画，不支持嵌套布局层次结构或 Activity 转换。
 */
class MotionLayoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMotionLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.button.setOnClickListener {
//            Log.d(TAG, "onCreate: button click") // 动画使用 OnClick 时不会执行 onClick 方法
//        }
        val list = mutableListOf<String>()
        for (i in 0..100) {
            list.add("item $i")
        }
        binding.recyclerView.adapter = MoreAdapter(this, false).apply { setData(list) }
    }
}