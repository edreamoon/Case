package com.ccino.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class ConstraintCustHelper : ConstraintHelper {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context?, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)

        val views = getViews(container)
        for (view in views) {
            val anim = ViewAnimationUtils.createCircularReveal(
                view, view.width / 2,
                view.height / 2, 0f,
                Math.hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat()
            )
            anim.duration = 3000
            anim.start()
        }
    }


}