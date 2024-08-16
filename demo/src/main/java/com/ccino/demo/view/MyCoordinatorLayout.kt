package com.ccino.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MyCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CoordinatorLayout(
    context, attrs, defStyle
) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("ccino", "onInterceptTouchEvent: ")
        return false
    }
}