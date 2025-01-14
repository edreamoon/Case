package com.ccino.demo.view.transition

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityTransitionBinding

/**
 *
 */
class TransitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransitionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransitionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeScene()
        binding.root.postDelayed({ transition2() }, 1000)
    }

    private fun transition2() {
        /*        // 设置动画方式 - 默认
                TransitionManager.beginDelayedTransition(rootView)
                button.visibility = View.VISIBLE

        // 设置动画方式 - 淡入淡出
                val fade = Fade().setDuration(1000)
                TransitionManager.beginDelayedTransition(rootView, fade)
                button.visibility = View.VISIBLE*/

        // 设置动画方式 - 右边滑动
        val slide = Slide(Gravity.END).setDuration(1000)
        TransitionManager.beginDelayedTransition(binding.main, slide)
        binding.buttonFirst.visibility = View.VISIBLE

    }


    private var isScene1 = false
    private fun changeScene() {
        //场景容器
        val sceneRoot = binding.flSceneRoot
        //变换前后的两个场景
        val scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene_1, this.baseContext)
        val scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene_2, this.baseContext)

        //默认先展示场景1
        TransitionManager.go(scene1)
        isScene1 = true

        binding.buttonFirst.setOnClickListener {
            val toScene = if (isScene1) scene2 else scene1
            isScene1 = !isScene1
            val changeBounds = ChangeBounds()
            TransitionManager.go(toScene, changeBounds)
        }
    }
}