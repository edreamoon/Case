package com.ccino.demo.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.ccino.demo.R
import com.ccino.demo.util.dp

class InspireLoading @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.chat_inspire_loading_bg)
    private val lightBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.chat_inspire_loading_light)
    private val bgRect = RectF()
    private val bgHeight = bgBitmap.height // 固定高度
    private val lightHeight = lightBitmap.height
    private val gap = 12.dp
    private val itemSize = 3
    private var slideLen = 0
    private var lightStart = 0.dp
    private var lightMarginEnd = 4.dp // 图标条的右边距
    private val lightYOffset = (bgHeight - lightHeight) / 2f - 2.dp
    private var lightXOffset = lightStart.toFloat() // 图标条的位置
    private var animLoading = false
    private val delayRunnable: Runnable = Runnable { animator.start() }

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 800
        addUpdateListener {
            val fraction = it.animatedValue as Float
            lightXOffset = lightStart + slideLen * fraction
            invalidate()

        }
        addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) = Unit
            override fun onAnimationEnd(animation: Animator) {
                if (animLoading) {
                    postDelayed(delayRunnable, 1000)
                }
            }

            override fun onAnimationCancel(animation: Animator) = Unit
            override fun onAnimationRepeat(animation: Animator) = Unit
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val hSpec = MeasureSpec.makeMeasureSpec(itemSize * bgHeight + (itemSize - 1) * gap, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, hSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgRect.set(0f, 0f, w.toFloat(), h.toFloat())
        slideLen = width - lightStart - lightMarginEnd
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until itemSize) {
            bgRect.top = (i * bgHeight + i * gap).toFloat()
            bgRect.bottom = bgRect.top + bgHeight
            canvas.drawBitmap(bgBitmap, null, bgRect, null)

            if (animLoading) {
                val lightTop = bgRect.top + lightYOffset
                canvas.drawBitmap(lightBitmap, lightXOffset, lightTop, null)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(delayRunnable)
        animator.cancel()
    }

    fun start() {
        animLoading = true
        animator.start()
    }

    fun end() {
        animLoading = false
        animator.end()
        removeCallbacks(delayRunnable)
    }
}
