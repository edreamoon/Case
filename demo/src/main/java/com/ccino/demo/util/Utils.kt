package com.ccino.demo.util

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import kotlin.math.roundToInt

/**
 * Created by ccino on 2021/9/8.
 */

private val metrics = Resources.getSystem().displayMetrics
val Number.dp: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            metrics
        ).roundToInt()
    }

val Number.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        metrics
    ).roundToInt()

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

/**
 * 小数位数 及 末尾去零处理
 */
fun floatAction(num: Int, decimal: Int): String {
    return if (decimal == 2) String.format("%.2f", num / 1000f).removeSuffix("0").removeSuffix("0")
        .removeSuffix(".")
    else String.format("%.1f", num / 1000f).removeSuffix("0").removeSuffix(".")
}

//fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(application, colorRes)
//
//fun getDimension(@DimenRes dimen: Int) = application.resources.getDimensionPixelSize(dimen)