package com.ccino.demo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ccino.demo.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar

/**
 * https://www.jianshu.com/p/7caa5f4f49bd
 */
class CoordinatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        coordinatorLayout()
        coordinatorAndAppBar()
//        collapsingToolBar()
//        scrollingActivity()
    }

    private fun scrollingActivity() {
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<View>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    /**
     * CollapsingToolbarLayout 出现的目的只是为了增强 Toolbar。
     * 它需要是 AppBarLayout 的直接子 View，这样才能发挥出效果
     */
    private fun collapsingToolBar() {
        setContentView(R.layout.activity_coordinator_collapsingtoolbar)
        initAdapter()
    }

    /**
     * AppBarLayout 是一个垂直的 LinearLayout，实现了 Scrolling Gestures 特性,只有作为 CoordinatorLayout 的直接子 View 时才能正常工作。
     * AppBarLayout 对象默认配置了一个 Behavior。而正是这个 Behavior，它会响应外部的嵌套滑动事件，然后根据特定的规则去伸缩和滑动内部的子 View
     * 1. AppBarLayout 的子 View 应该声明想要具有的“滚动行为”，这可以通过 layout_scrollFlags 属性或是 setScrollFlags() 方法来指定。
     * 2.需要和一个独立的兄弟"Nested Scroll View"配合使用(注意Nested Scroll 含义，支持滑动的View不一定是同层级，如此例被FrameLayout包裹但处理嵌套滑动的是RecyclerView),这样 AppBarLayout 才能知道什么时候开始滑动。
     * 3.Scrolling View 和 AppBarLayout 之间的关联，通过将 Scrolling View 的 Behavior 设为 AppBarLayout.ScrollingViewBehavior 来建立。
     */
    @SuppressLint("MissingInflatedId")
    private fun coordinatorAndAppBar() {
        setContentView(R.layout.activity_coordinator_appbar)
//        findViewById<ViewPager2>(R.id.bannerPager).adapter = BannerAdapter()
        /*1. setScrollFlags()  or xml: app:layout_scrollFlags="scroll"
         val params = toolBar.layoutParams as AppBarLayout.LayoutParams
         params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL*/
        /**
         * noScroll:默认,本View固定位置，不会移动
         *
         * scroll:效果同ForumScrollView. 下滑时Scrolling View滑出全部再滑动本view; 上滑时本view随ScrollView一起。
         * 下面其它标签均是配合此Flag使用的
         * snap:在一次滚动结束时，本 View 很可能只处于“部分显示”的状态，加上这个标记能够达到“要么完全隐藏，要么完全显示”的效果。
         * enterAlways:下滑时，本View先滑出全部后再滑动Scrolling View; 上滑时随Scrolling View一起
         * enterAlwaysCollapsed：enterAlways的附加值.1. 有enterAlways才生效，2. 有minHeight；与enterAlways滑动效果唯一不同点：
         *   滑时本View滑动露出minHeight时，滑动Scrolling View;上滑时本view随ScrollView一起
         * exitUntilCollapsed: 1. 有minHeight。效果同scroll,但本View滑动范围Height - minHeight
         */
//        toolBar.title = "标题title"
//        toolBar.subtitle = "子标题subtitle"
//        toolBar.setNavigationIcon(R.mipmap.icon)

        //2. Scrolling View
        initAdapter()
/*
        //3. AppBarLayout.ScrollingViewBehavior or xml:  app:layout_behavior="@string/appbar_scrolling_view_behavior"
        val recyclerParams = recyclerView.layoutParams as CoordinatorLayout.LayoutParams
        recyclerParams.behavior = ScrollingViewBehavior()*/

        /**
         *监听AppBarLayout滚动: 上滑 0->-toolBarHeight; 下滑-toolBarHeight->0
         */
        findViewById<AppBarLayout>(R.id.appBar).addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
//            appBarLayout.totalScrollRange
//            val toolBarHeight = toolBar.height
//            Log.d("CoordinatorActivity", "coordinatorAndAppBar: $offset")
//            if (abs(offset) <= toolBarHeight) {
//                appBarLayout.alpha = 1.0f - abs(offset) * 1.0f / toolBarHeight
//            }
        })
    }

    private fun initAdapter() {
        val adapter = MoreAdapter(this, false)
        val list = mutableListOf<String>()
        for (i in 1..20) {
            list.add(i.toString())
        }
        adapter.setData(list)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
    }

    /**
     * CoordinatorLayout 相当于一个增强版的 FrameLayout，通过为 CoordinatorLayout 的子 View 指定 Behavior， 就可以实现它们之间的交互行为。
     */
    private fun coordinatorLayout() {
        setContentView(R.layout.activity_coordinator)
        findViewById<View>(R.id.btn).setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.x = event.rawX - v.width / 2
                v.y = event.rawY - v.height / 2
            }
            true
        }

    }
}
