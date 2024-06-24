package com.ccino.demo.sdk

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.DragEvent
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityFrameworkBinding


private const val TAG = "FrameworkActivity"

class FrameworkActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var binding: ActivityFrameworkBinding

    private fun getAttachedWindow(context: Context): Window? {
        if (context is Activity) {
            return context.window
        } else if (context is ContextThemeWrapper) {
            val baseContext: Context = context.baseContext
            return getAttachedWindow(baseContext)
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textView = binding.threadView
        textView.setOnClickListener { createViewInThread(this) }

        // error case: 重影
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_holder, DemoFragment())
        transaction.commit()
        dragOpenActivity()
    }

    private fun dragOpenActivity() {
        val dragTextView = binding.dragView
        dragTextView.setOnLongClickListener { view ->
            val item = ClipData.Item(view.tag as? CharSequence)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(view.tag as? CharSequence, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(view)
            view.startDragAndDrop(data, dragShadowBuilder, view, 0)

            true
        }

        dragTextView.tag = "This is the data to be dragged"

        // Optionally, you can handle the drag events
        dragTextView.setOnDragListener { view, dragEvent ->
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        view.invalidate()
                        true
                    } else {
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> true
                DragEvent.ACTION_DRAG_EXITED -> {
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    val item = dragEvent.clipData.getItemAt(0)
                    val dragData = item.text
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("dragData", dragData)
                    startActivity(intent)
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    view.invalidate()
                    true
                }
                else -> false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        updateUiViewInThread(textView)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }
}