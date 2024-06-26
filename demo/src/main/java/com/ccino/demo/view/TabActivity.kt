package com.ccino.demo.view

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ccino.demo.databinding.ActivityTabBinding
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
    }

    private val tabs: Array<String> = arrayOf("朋友", "闲人", "我的地","我的地")
    private fun initTabLayout() {
        val adapter = EntryPageAdapter(this)
        binding.viewpager.adapter = adapter
        val tabLayout = binding.tabLayout2
        TabLayoutMediator(tabLayout, binding.viewpager) { tab, position ->
            // 取消长按toast
            tab.view.setOnLongClickListener { true }
            tab.view.tooltipText = null
            tab.setText(tabs[position])
        }.attach()

        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setTabText(position)
            }
        })

        /* val tabs = tabLayout.getChildAt(0) as ViewGroup

         for (i in 0 until tabs.childCount - 1) {
             val tab = tabs.getChildAt(i)
             val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
             layoutParams.weight = 0f
 //            layoutParams.marginEnd = 40.dp
 //            layoutParams.marginStart = 12.dp
             layoutParams.width = LayoutParams.WRAP_CONTENT
             tab.layoutParams = layoutParams
             tabLayout.requestLayout()
         }*/
    }


    // 字体加粗
    private fun setTabText(tab: Int) {
        val tabLayout = binding.tabLayout
        for (i in 0 until tabLayout.tabCount) {
            val at = tabLayout.getTabAt(i)
            val itemView = at?.view
//            itemView?.setBackgroundColor(if(i ==0) Color.BLUE else if(i == 1) Color.RED else Color.CYAN)
            val view = at?.view?.getChildAt(1) as? TextView
            val selected = i == tab
            view?.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
        }
    }
}