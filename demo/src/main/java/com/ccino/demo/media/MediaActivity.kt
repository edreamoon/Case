package com.ccino.demo.media

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ccino.demo.R
import com.ccino.demo.databinding.ActivityMediaBinding

private const val TAG = "MediaActivity"

class MediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val imageView = binding.verWidthSmallView
        imageView.post {
            val matrix = imageView.imageMatrix
//            val bounds = imageView.drawable.bounds
//            Log.d(TAG, "onCreate: $matrix")
//            Log.d(TAG, "onCreate: bound=$bounds")
//            val viewW = imageView.width
//            val bitW = bounds.width().toFloat()

            val scale = 2.4f//viewW / bitW
            Log.d(TAG, "onCreate: scale=$scale")
            matrix.setScale(scale, scale)
            imageView.imageMatrix = matrix
            imageView.setImageResource(R.drawable.ver_w200)
        }
    }
}