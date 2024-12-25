package com.ccino.demo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.ccino.demo.R
import kotlin.math.cos
import kotlin.math.sin

class GradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var gradientColors: IntArray = intArrayOf(Color.RED, Color.BLUE)
    private var gradientPositions: FloatArray? = null
    private var gradientAngle: Float = 0f
    private var gradient: LinearGradient? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GradientView, 0, 0).apply {
            try {
                val colorsResId = getResourceId(R.styleable.GradientView_gradientColors, 0)
                if (colorsResId != 0) {
                    gradientColors = resources.getIntArray(colorsResId)
                }

                val positionsResId = getResourceId(R.styleable.GradientView_gradientPositions, 0)
                if (positionsResId != 0) {
                    val typedArray = resources.obtainTypedArray(positionsResId)
                    gradientPositions = FloatArray(typedArray.length()) { index ->
                        typedArray.getFloat(index, 0f)
                    }
                    typedArray.recycle()
                }

                gradientAngle = getFloat(R.styleable.GradientView_gradientAngle, 0f)
            } finally {
                recycle()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        createGradient(w, h)
    }

    private fun createGradient(width: Int, height: Int) {
        val radians = Math.toRadians(gradientAngle.toDouble())
        val xStart = (cos(radians) * width / 2).toFloat()
        val yStart = (sin(radians) * height / 2).toFloat()

        val xEnd = width - xStart
        val yEnd = height - yStart

        gradient = LinearGradient(
            xStart, yStart, xEnd, yEnd,
            gradientColors,
            gradientPositions,
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}