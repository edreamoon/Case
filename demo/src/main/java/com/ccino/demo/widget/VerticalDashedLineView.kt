package com.ccino.demo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.ccino.demo.util.dp

private val dash_gap = 2.dp.toFloat()

class VerticalDashedLineView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.color = 0xFFE9B6E9.toInt()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.dp.toFloat()
        paint.pathEffect = DashPathEffect(floatArrayOf(dash_gap, dash_gap), 0f)
    }

    val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        path.moveTo(width / 2, 0f)
        path.lineTo(width / 2, height)
        canvas.drawPath(path, paint)
    }
}