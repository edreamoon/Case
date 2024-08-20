package com.ccino.demo.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.absoluteValue

private const val TAG = "NestedScrollableHost2"

/**
 * 还是必须使用 isUserInputEnabled 代替 disallowIntercept 才能解决 vp2嵌套vp2问题，或者外部使用vp(过时)代替vp2
 */
class NestedScrollableHost2 : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            parentViewPager?.isUserInputEnabled = true
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${e.action}")
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private var initialX = 0f
    private var initialY = 0f
    private fun handleInterceptTouchEvent(e: MotionEvent) {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = e.x
                initialY = e.y
                parentViewPager?.isUserInputEnabled = false
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = e.x - initialX
                val dy = e.y - initialY

                // assuming ViewPager2 touch-slop is 2x touch-slop of child
                val scaledDx = dx.absoluteValue
                val scaledDy = dy.absoluteValue
                val b = scaledDx >= scaledDy
                parentViewPager?.isUserInputEnabled = !b
            }

            else -> {
                parentViewPager?.isUserInputEnabled = true
            }
        }
    }
}