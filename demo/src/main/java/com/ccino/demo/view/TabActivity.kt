package com.ccino.demo.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityTabBinding
import com.ccino.demo.util.dp
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


private const val TAG = "TabActivity"

class TabActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTabLayout()
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

    private val tabs: Array<String> = arrayOf("朋友", "闲人", "我的地")
    private fun initTabLayout() {
        val adapter = EntryPageAdapter(this)
        binding.viewpager.adapter = adapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewpager) { tab, position ->
            tab.view.setOnLongClickListener { true }
            tab.view.tooltipText = null
            tab.setText(tabs[position])
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setTabText(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        val tabs = tabLayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount - 1) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 240.dp
//            layoutParams.marginStart = 12.dp
//            layoutParams.width = 10.dp
            tab.layoutParams = layoutParams
            tabLayout.requestLayout()
        }
    }

    private fun setTabText(tab: TabLayout.Tab?) {
        val tabLayout = binding.tabLayout
        for (i in 0 until tabLayout.tabCount) {
            val at = tabLayout.getTabAt(i)
            val itemView = at?.view
//            itemView?.setBackgroundColor(if(i ==0) Color.BLUE else if(i == 1) Color.RED else Color.CYAN)
            val view = at?.view?.getChildAt(1) as? TextView
            val selected = at == tab
            view?.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
        }
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