package com.ccino.demo.media
import com.google.android.exoplayer2.upstream.DataSource
import java.io.InputStream

class InputStreamDataSourceFactory(private val inputStream: InputStream) : DataSource.Factory {
    override fun createDataSource(): DataSource {
        return StreamDataSource(inputStream)
    }
}
