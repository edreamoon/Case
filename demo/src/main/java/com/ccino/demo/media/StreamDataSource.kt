package com.ccino.demo.media

import android.net.Uri
import com.google.android.exoplayer2.C.LENGTH_UNSET
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.TransferListener
import java.io.IOException
import java.io.InputStream

class StreamDataSource(private val inputStream: InputStream) : DataSource {

    override fun addTransferListener(transferListener: TransferListener) {
//        TODO("Not yet implemented")
    }

    private var isOpen = false
    private var bytesRead = 0L

    override fun open(dataSpec: DataSpec): Long {
        if (isOpen) throw IOException("DataSource already open")
        isOpen = true
        return LENGTH_UNSET.toLong() // Unknown length
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (!isOpen) throw IOException("DataSource not open")
        val bytesReadNow = inputStream.read(buffer, offset, length)
      /*  if (bytesReadNow == -1) {
            // End of stream
            return C.RESULT_END_OF_INPUT
        }*/
        bytesRead += bytesReadNow
        return bytesReadNow
    }

    override fun close() {
        if (isOpen) {
            inputStream.close()
            isOpen = false
        }
    }

    override fun getUri(): Uri? = null
}