package com.ware.third

import android.view.View
import okio.Okio
import okio.source
import java.io.File

/**
 * Created by jianfeng.li on 20-1-14.
 */
class Test {
    private fun tt() {
        val source = File("abc").source()
        val view = View(null)
        view.hahaha {

        }

        view.hahaha2 {
            this
        }
    }

    fun <T : View> T.hahaha(f: () -> Unit) {
//        this
    }
    fun <T : View> T.hahaha2(f: T.() -> Unit) {
//        this
    }



}