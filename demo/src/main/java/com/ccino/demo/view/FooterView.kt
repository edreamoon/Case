package com.ccino.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ccino.demo.databinding.CommonLayoutFooterNoDataBinding


class FooterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = CommonLayoutFooterNoDataBinding.inflate(LayoutInflater.from(context), this, true)
}