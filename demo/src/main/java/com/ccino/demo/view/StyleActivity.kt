package com.ccino.demo.view

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.blurview.RenderScriptBlur
import com.ccino.demo.databinding.ActivityStyleBinding
import com.ccino.demo.databinding.LayoutBlurViewBinding

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
//        setupBlurView()
//        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            binding.blurView.invalidate()
//        }

//        binding.blurView.postDelayed({
//            binding.blurView.refreshBG(binding.coverView)
//        }, 1000)
//
//
//        binding.nestView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            binding.blurView.refreshBG(binding.coverView)
//            binding
//        }
    }

//    private fun setupBlurView() {
//        val radius = 20f
//
//        // 获取根视图
//        val rootView = binding.backgroundImageView.parent as ViewGroup
//
//        // 设置背景，如果没有背景则使用透明背景
//        val windowBackground: Drawable = binding.backgroundImageView.drawable
//            ?: ContextCompat.getDrawable(this, android.R.color.transparent)!!
//
//        binding.blurView.setupWith(rootView)
////            .setFrameClearDrawable(windowBackground)
//            .setBlurRadius(radius)
//            .setOverlayColor(-0x80000000)
//
//    }
}
