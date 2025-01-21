package com.ccino.demo.media
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.upstream.BaseDataSource
import com.google.android.exoplayer2.upstream.DataSpec
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue

class DynamicByteArrayDataSource : BaseDataSource(false) {
    private val dataQueue = LinkedBlockingQueue<ByteArray>() // 数据队列
    private var currentData: ByteArray? = null
    private var currentOffset = 0
    private var isOpen = false

    @Synchronized
    override fun open(dataSpec: DataSpec): Long {
        if (isOpen) throw IOException("DataSource already open")
        isOpen = true
        currentData = dataQueue.poll()
        currentOffset = 0
        return C.LENGTH_UNSET.toLong() // 数据长度未知
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (!isOpen) throw IOException("DataSource is not open")

        // 如果当前块为空，尝试从队列获取下一个块
        while (currentData == null) {
            currentData = dataQueue.poll()
            if (currentData == null) {
                return C.RESULT_NOTHING_READ // 暂时没有数据
            }
            currentOffset = 0
        }

        val remaining = currentData!!.size - currentOffset
        val bytesToRead = minOf(length, remaining)

        // 将数据复制到 buffer 中
        System.arraycopy(currentData, currentOffset, buffer, offset, bytesToRead)
        currentOffset += bytesToRead

        // 如果当前块已读完，切换到下一个块
        if (currentOffset >= currentData!!.size) {
            currentData = null
        }

        return bytesToRead
    }

    override fun close() {
        if (isOpen) {
            isOpen = false
            currentData = null
            currentOffset = 0
        }
    }

    override fun getUri() = Uri.EMPTY

    fun appendData(data: ByteArray) {
        dataQueue.offer(data)
    }
}
