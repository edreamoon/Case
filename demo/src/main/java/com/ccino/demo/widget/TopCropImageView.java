package com.ccino.demo.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.google.android.material.imageview.ShapeableImageView;

/**
 * 顶部保留、裁掉底部
 */
public class TopCropImageView extends ShapeableImageView {

    public TopCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        if (getDrawable() == null) {
            return super.setFrame(l, t, r, b);
        }
        Matrix matrix = getImageMatrix();
        float scaleWidth = getWidth() / (float) getDrawable().getIntrinsicWidth();
        float scaleHeight = getHeight() / (float) getDrawable().getIntrinsicHeight();
        float scaleFactor = (scaleWidth > scaleHeight) ? scaleWidth : scaleHeight;
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        if (scaleFactor == scaleHeight) {
            float tanslateX = ((getDrawable().getIntrinsicWidth() * scaleFactor) - getWidth()) / 2;
            matrix.postTranslate(-tanslateX, 0);
        }
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }
}