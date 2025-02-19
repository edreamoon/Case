package com.ccino.demo.view

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityWidgetBinding
import com.google.android.material.shape.ShapeAppearanceModel

class WidgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWidgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWidgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        blurView()
//        binding.heartFillView.setProgress(0.1f)
//        binding.mb2.setOnClickListener { binding.heartFillView.setProgress(0.5f) }
        binding.start.setOnClickListener { binding.loadingView.start() }
        binding.end.setOnClickListener { binding.loadingView.end() }
        val list = mutableListOf<String>()
        for (i in 1..20) {
            list.add(i.toString())
        }
        val adapter = MoreAdapter(this, false)
        adapter.setData(list)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.postDelayed({binding.recyclerView.setNoMore()},2000)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(ParallelogramShape())
            .build()

        // 应用到 ShapeableImageView
        binding.shapeableImageView.shapeAppearanceModel = shapeAppearanceModel
    }

    private fun blurView() {
        /*  //RenderEffect android 12及以上才可以，radius越大越模糊
          val blurView = binding.blurView
          blurView.setRenderEffect(RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP))
  */
        // 全兼容blur：view能实时(滑动时)根据背景来blur
        val rootView = window?.decorView as? ViewGroup ?: return
        binding.blurBg.setupWith(rootView)
        //如何显示圆角：1.设置background drawable,2.设置如下两行代码
        binding.blurBg.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurBg.setClipToOutline(true)
//        blurBgView.setupWith(rootView)
    }
}