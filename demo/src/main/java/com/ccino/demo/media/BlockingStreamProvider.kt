package com.ccino.demo.media

import java.io.InputStream
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

data class StreamInfo(
    val inputStream: InputStream,
    val isLastStream: Boolean
)

interface StreamProvider {
    fun getNextStream(): StreamInfo
}

class BlockingStreamProvider : StreamProvider {
    private val streamQueue: BlockingQueue<StreamInfo> = LinkedBlockingQueue()

    // 添加新流到队列
    fun addStream(inputStream: InputStream, isLast: Boolean) {
        streamQueue.put(StreamInfo(inputStream, isLast))
    }

    // 获取下一个流（阻塞）
    override fun getNextStream(): StreamInfo {
        return streamQueue.take() // 如果队列为空，会阻塞等待
    }
}