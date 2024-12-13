package com.ccino.demo.view

import android.util.Log
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapePath

private const val TAG = "ParallelogramShape"
class ParallelogramShape : CutCornerTreatment() {

    override fun getCornerPath(shapePath: ShapePath, angle: Float, interpolation: Float, radius: Float) {
        Log.d(TAG, "getCornerPath: angle=$angle, interpolation=$interpolation, radius=$radius")
        val width = 300.toFloat()
        val height = 450.toFloat()
        shapePath.reset(0f,0f)
        shapePath.lineTo(width, 0f) // 返回到右上角
        shapePath.lineTo(width / 2, height) // 到底边中点
    }

//    override fun getPathForSize(width: Float, height: Float, path: Path) {
//        val build = ShapeAppearanceModel.builder().setAllCorners(CutCornerTreatment()).set.build()
//        val shapePath = ShapePath()
//        shapePath.lineTo(width - skewAmount, 0f) // Top-right
//        shapePath.lineTo(width, height)         // Bottom-right
//        shapePath.lineTo(skewAmount, height)    // Bottom-left
//        shapePath.lineTo(0f, 0f)                // Top-left
//        shapePath.close()
//        path.set(shapePath.path)
//    }
}
