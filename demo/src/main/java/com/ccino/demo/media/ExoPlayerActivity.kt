package com.ccino.demo.media

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ccino.demo.databinding.ActivityExoplayerBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.ByteArrayDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource


private const val TAG = "ExoPlayerLog"

class ExoPlayerActivity : AppCompatActivity() {
//    TransferRtpDataChannel
    private lateinit var bind: ActivityExoplayerBinding
    private val player by lazy { ExoPlayer.Builder(this).build() }
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityExoplayerBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.add.setOnClickListener { addItem("https://v-cdn.zjol.com.cn/276982.mp4") } // 验证在播放时是否能缓冲新添加的视频
        bind.continuePlay.setOnClickListener { streamPlay() } // 验证在播放时是否能缓冲新添加的视频
    }

    private fun streamPlay() {

        val inputStream = assets.open("ogg.ogg")
/// 假设你的媒体数据以 byte[] 的形式存储
        val mediaData: ByteArray = inputStream.readBytes()//loadMediaData() // 例如从文件或网络读取数据

// 创建 ByteArrayDataSource
        val byteArrayDataSource = ByteArrayDataSource(mediaData)

// 创建 ProgressiveMediaSource 工厂
        val mediaSourceFactory = ProgressiveMediaSource.Factory { byteArrayDataSource }

// 创建媒体源
        val mediaSource = mediaSourceFactory.createMediaSource(MediaItem.fromUri("bytearray://media"))
// 准备并播放
        player.setMediaSource(mediaSource)


        /*// 创建 DynamicByteArrayDataSource
        val dynamicDataSource = DynamicByteArrayDataSource()
// 创建媒体源
        val mediaSourceFactory = ProgressiveMediaSource.Factory { dynamicDataSource }
        val mediaSource = mediaSourceFactory.createMediaSource(MediaItem.fromUri("dynamic://media"))
        dynamicDataSource.appendData(inputStream.readBytes())

        player.setMediaSource(mediaSource)*/
        player.prepare()
        player.play()

// 动态添加数据
//        dynamicDataSource.appendData(secondByteArray)
//        player.prepare()
//        player.play()
    }

    private fun play() {
        val videoUri = "https://v-cdn.zjol.com.cn/276972.mp4"
        val mediaItem: MediaItem = MediaItem.fromUri(videoUri)  // Build the media item.
        player.setMediaItem(mediaItem)  // Set the media item to be played.
        bind.playerView.player = player
        player.prepare() // Prepare the player.
        player.play() // Start the playback.
    }

    private fun addItem(url: String) {
        val mediaItem: MediaItem = MediaItem.fromUri(url) // 构建新的媒体项
        player.addMediaItem(mediaItem) // 添加到播放列表

        // 如果播放器已结束播放，需要手动重置播放状态
        if (player.playbackState == Player.STATE_ENDED) {
            player.seekToDefaultPosition() // 重置到播放起点
            player.playWhenReady = true // 开始播放
        } else if (!player.isPlaying) {
            player.playWhenReady = true // 如果暂停状态，则恢复播放
        }
    }

    // 添加视频到播放列表
    private fun addMediaSource(url: String) {
        val mediaSource = buildMediaSource(url)
        concatenatingMediaSource.addMediaSource(mediaSource)
        player.playWhenReady = true // 自动播放
        player.play()
    }

    // 构建 MediaSource
    private fun buildMediaSource(url: String): MediaSource {
        val mediaItem = MediaItem.fromUri(url)
        return ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this))
            .createMediaSource(mediaItem)
    }

    // 重播功能
    private fun replay() {
        player.seekToDefaultPosition(0) // 从第一个视频开始
        player.playWhenReady = true // 开始播放
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
