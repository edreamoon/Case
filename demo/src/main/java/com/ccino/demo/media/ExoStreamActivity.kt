package com.ccino.demo.media

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityExoStreamBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.io.File

private const val TAG = "ExoStreamActivity"

class ExoStreamActivity : AppCompatActivity() {
    private lateinit var bind: ActivityExoStreamBinding
    private val player by lazy { ExoPlayer.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityExoStreamBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.add.setOnClickListener { play() } // 验证在播放时是否能缓冲新添加的视频
    }

    private fun play() {

        File(getExternalFilesDir(null), "audio_0b.wav")
        val uri = Uri.fromFile(File(getExternalFilesDir(null), "audio_0b.wav"))
        Log.d(TAG, "play: uri=$uri")
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)  // Set the media item to be played.
        bind.playerView.player = player
        player.prepare() // Prepare the player.
        player.play() // Start the playback.

    }


    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}