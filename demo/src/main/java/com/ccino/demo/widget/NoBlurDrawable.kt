package com.ccino.demo.widget

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Log

private const val TAG = "NoBlurDrawable"

class NoBlurDrawable(private val color: Int, private val blurRadius: Float) : Drawable() {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = this@NoBlurDrawable.color
//        maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        canvas.drawRect(bounds, paint)
    }

    override fun setAlpha(alpha: Int) {
        Log.d(TAG, "setAlpha: $alpha")
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }
}