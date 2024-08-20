package com.ccino.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

private const val TAG = "MyConstraint"
class MyConstraint @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(
    context, attrs, defStyle
) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent MyConstraint: ${ev?.action}")
        return super.onInterceptTouchEvent(ev)
    }

}