package com.ccino.demo.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ccino.demo.R
import com.google.android.material.appbar.AppBarLayout
import com.ware.widget.recycler.MoreAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun coordinatorAndAppBar() {

        val pager2 = view?.findViewById<ViewPager2>(R.id.bannerPager)!!

        pager2?.adapter = BannerAdapter()
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
        view?.findViewById<AppBarLayout>(R.id.appBar)?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
//            appBarLayout.totalScrollRange
//            val toolBarHeight = toolBar.height
//            Log.d("CoordinatorActivity", "coordinatorAndAppBar: $offset")
//            if (abs(offset) <= toolBarHeight) {
//                appBarLayout.alpha = 1.0f - abs(offset) * 1.0f / toolBarHeight
//            }
        })
    }

    private fun initAdapter() {
        val adapter = MoreAdapter(requireContext(), false)
        val list = mutableListOf<String>()
        for (i in 1..20) {
            list.add(i.toString())
        }
        adapter.setData(list)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_coordinator_appbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinatorAndAppBar()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BlankFragment().apply {

            }
    }
}