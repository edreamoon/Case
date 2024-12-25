package com.ccino.demo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.ccino.demo.R

/**
 * 绘制 drawable 指定区域，其实就是看不到的部分不在 canvas 的可视区域内，并没有真正的裁剪
 */
class CropDrawable @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.bit)

    private val radiusPath = Path()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawable?.let {
//            it.setBounds(0, 0, width, height) // 如果 view 宽高与 drawable 不是等比例，会有拉伸
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight) // 不会拉伸 drawable

            canvas.save()
            radiusPath.addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 20f, 20f, Path.Direction.CW)
            canvas.clipPath(radiusPath)
            canvas.translate(-(it.intrinsicWidth - width).toFloat(), -(it.intrinsicHeight - height).toFloat())
            it.draw(canvas)
            canvas.restore()
        }
    }
}
