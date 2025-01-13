package com.ccino.demo.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityConstraintBinding

private const val TAG = "ConstraintActivity"

/**
 * https://juejin.cn/post/6844903872255754248#heading-4
 * Layer： 继承 ConstraintHelper(group/barrier 也是 helper)， 可以看作是它引用的 view 的边界（可以理解为包含这些 view 的一个 ViewGroup，但是Layer并不是ViewGroup，
 * Layer并不会增加 view 的层级）。另外Layer支持对里面的 view 一起做变换。
 *
 * ConstraintHelper 为什么需要自定义?
 *
 * 保持 view 的层级不变，不像 ViewGroup 会增加 view 的层级
 * 封装一些特定的行为，方便复用
 * 一个 View 可以被多个 Helper引用，可以很方便组合出一些复杂的效果出来
 *
 * 如何自定义?
 * Helper持有 view 的引用，所以可以获取 view (getViews)然后操作 view
 * 提供了 onLayout 前后的 callback（updatePreLayout/updatePreLayout）
 * Helper 继承了 view，所以Helper本身也是 view
 *
 * motionlayout
 * https://juejin.cn/post/7047677252049305613
 * https://blog.csdn.net/yingaizhu/article/details/107078352
 *
 */
class ConstraintActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConstraintBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityConstraintBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.layer.setOnClickListener {
//            Log.d(TAG, "layer click")
//        }

        binding.filterView.postDelayed({
           binding.holder.setContentId(binding.Ac.id)
        }, 5000)
    }
}