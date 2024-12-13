package com.ccino.demo.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.NinePatchDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.ccino.demo.R
import com.ccino.demo.util.dp

private const val TAG = "StoryBgView"

class StoryBgView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    private val ninePatchDrawable = ContextCompat.getDrawable(context, R.drawable.sq_recommend_story_top) as? NinePatchDrawable
    private val gridBitmap = BitmapFactory.decodeResource(resources, R.drawable.sq_recommend_story_grid)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path()
    private val topLeftRadius = 4.dp.toFloat()
    private val rightTopRadius = 8.dp.toFloat()
    private val rectSrc = Rect()
    private val rectDst = Rect()
    private val gridOffset = 2.dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gridBit = gridBitmap ?: return
        val borderBit = ninePatchDrawable ?: return
        if (width == 0 || height == 0) return

        val gridWidth = gridBit.width
        val gridHeight = gridBit.height
        val leftOffset = (gridWidth - width).coerceAtLeast(0)
        val topOffset = gridHeight - height.coerceAtLeast(0)

        canvas.save()
        canvas.clipPath(clipPath.apply {
            reset()
            addRoundRect(
                0f, 0f, width.toFloat(), height.toFloat(),
                floatArrayOf(
                    topLeftRadius, topLeftRadius,
                    rightTopRadius, rightTopRadius,
                    0f, 0f, rightTopRadius, rightTopRadius,
                ),
                Path.Direction.CW
            )
        })
        rectSrc.set(leftOffset, topOffset, gridWidth, gridHeight)
        rectDst.set(0, 0, width - gridOffset, height - gridOffset)
        canvas.drawBitmap(gridBitmap, rectSrc, rectDst, paint)
        canvas.restore()

        borderBit.setBounds(0, 0, width, height)
        borderBit.draw(canvas)
    }
}