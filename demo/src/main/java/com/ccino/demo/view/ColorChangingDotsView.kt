package com.ccino.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
import com.ccino.demo.util.dp

private const val INTERVAL = 300L

class ColorChangingDotsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val dotRadius = 3.dp
    private val diameter = dotRadius shl 1
    private val dotSpacing = 6.dp
    private var currentColorIndex = 0
    private val vWidth = diameter * 3 + dotSpacing * 2
    //    private val vHeight = diameter
    private val colors = arrayOf(
        arrayOf(0xffd5d5d6.toInt(), 0xffababad.toInt(), 0xff818184.toInt()),
        arrayOf(0xff818184.toInt(), 0xffd5d5d6.toInt(), 0xffababad.toInt()),
        arrayOf(0xffababad.toInt(), 0xff818184.toInt(), 0xffd5d5d6.toInt()),
    )
    private var changeColorRunnable: Runnable? = null

    init {
        paint.style = Paint.Style.FILL
        changeColorRunnable = Runnable {
            currentColorIndex = (currentColorIndex + 1) % colors.size
            invalidate()
            postDelayed(changeColorRunnable, INTERVAL)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(vWidth, MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startX = dotRadius
        val centerY = height shr 1

        val arr = colors[currentColorIndex]
        for (i in 0..2) {
            paint.color = arr[i]
            val cx = startX + i * (dotSpacing + diameter)
            canvas.drawCircle(cx.toFloat(), centerY.toFloat(), dotRadius.toFloat(), paint)
        }
    }

    private fun startAnim() {
        removeCallbacks(changeColorRunnable)
        postDelayed(changeColorRunnable, INTERVAL)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        if (isVisible && isAttachedToWindow) {
            startAnim()
        } else {
            removeCallbacks(changeColorRunnable)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnim()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(changeColorRunnable)
    }
}