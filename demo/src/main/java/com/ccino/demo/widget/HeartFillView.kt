package com.ccino.demo.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.ccino.demo.R
import com.ccino.demo.util.dp

class HeartFillView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = true }
    private lateinit var bgBitmap: Bitmap // 半透明心形图片
    private val fillBitmap = BitmapFactory.decodeResource(resources, R.drawable.heart_full) // 红色心形图片
    private val wavePath = Path()
    private var progress = 0.5f // 固定进度
    private var waveOffset = 0f
    private val waveAmplitude = 1.5.dp // 波浪高度较低
    private val waveLength = 15.dp.toFloat()
    private var waveDirection = 1f // 1 表示正向，-1 表示逆向
    private val verSpace = 0 //5.dp // 图片垂直方向透明占位
    private val ver2Space = verSpace * 2
    private val waveStep = 1.dp

    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!::bgBitmap.isInitialized) return

        val left = (width - bgBitmap.width) / 2f
        val top = (height - bgBitmap.height) / 2f

        // Draw the heart outline bitmap
        canvas.drawBitmap(bgBitmap, left, top, paint)
        if (progress <= 0) return

        // Draw the filling wave
        val waveHeight = (bgBitmap.height - ver2Space) * progress
        val waveTop = top + bgBitmap.height - waveHeight - verSpace

        wavePath.reset()
        wavePath.moveTo(left - waveOffset, waveTop)
        for (i in 0..bgBitmap.width step waveLength.toInt()) {
            wavePath.rQuadTo(waveLength / 2, -waveAmplitude * waveDirection, waveLength, 0f)
            wavePath.rQuadTo(waveLength / 2, waveAmplitude * waveDirection, waveLength, 0f)
        }
        wavePath.lineTo(left + bgBitmap.width, waveTop)
        wavePath.lineTo(left + bgBitmap.width, top + bgBitmap.height)
        wavePath.lineTo(left, top + bgBitmap.height)
        wavePath.close()

        val saveCount = canvas.saveLayer(left, top, left + bgBitmap.width, top + bgBitmap.height, paint)
        canvas.drawPath(wavePath, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(fillBitmap, left, top, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveCount)
    }

    private val runnable = object : Runnable {
        override fun run() {
            waveOffset += waveStep
            if (waveOffset > waveLength) {
                waveOffset = 0f
                waveDirection *= -1 // 反转波浪方向
            }
            Log.d("ccino", "run: ")
            invalidate()
            handler.postDelayed(this, 50)
        }
    }
    private val handler = Handler(Looper.getMainLooper())
    private fun startWaveAnimation() {
        handler.post(runnable)
    }

    fun setProgress(progress: Float, isTitle: Boolean = true) {
        this.progress = progress
        bgBitmap = BitmapFactory.decodeResource(
            resources, when {
                progress < 0 -> R.drawable.heart_lock
                isTitle -> R.drawable.heart_empty_title
                else -> R.drawable.heart_empty_pane
            }
        )
        if (progress <= 0) {
            handler.removeCallbacksAndMessages(null)
            invalidate()
        } else {
            startWaveAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacksAndMessages(null)
    }
}