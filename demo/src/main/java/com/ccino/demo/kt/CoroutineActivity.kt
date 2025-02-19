package com.ccino.demo.kt

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.ccino.demo.ui.theme.CaseTheme
import com.ccino.demo.ui.widget.Label
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

private const val TAG = "CoroutineActivity"

class CoroutineActivity : ComponentActivity(), CoroutineScope by MainScope() {

    private fun scopeMethod() {
        Log.d(TAG, "scopeMethod: before")
        launch {
            val ret = coroutineScope()
            Log.d(TAG, "scopeMethod: ret=$ret")
        }
        Log.d(TAG, "scopeMethod: after")
    }

    private suspend fun coroutineScope(): Int {
        Log.d(TAG, "coroutineScope: before")
        val ret = coroutineScope {
            Log.d(TAG, "coroutineScope: job before")
            val job = async {
                delay(5000)
                1
            }
            val await = job.await()
            Log.d(TAG, "coroutineScope: job after")
            await
        }
        Log.d(TAG, "coroutineScope: after")
        return ret
    }


    private val handler = Handler(Looper.getMainLooper())

    private fun invokeOnCompletion() {
        val job = launch {
            delay(4000)
            if (System.currentTimeMillis() > 0) return@launch //即使return invokeOnCompletion 也会执行
            Log.d(TAG, "completion: ")
        }.apply {
            invokeOnCompletion {
                //在 launch 里的线程回调，如果是在主线程会通过handler派发回调，所以不能保证顺序，即使取消也会回调
                Log.d(TAG, "completion: $it")//it 为 CancellationException 里的message
//                printStackTrace("invokeOnCompletion")
            }
        }

        handler.postDelayed({
            job.cancel(CancellationException("manual cancel"))
        }, 2000)
    }

    private fun dispatcher() {
        launch(Dispatchers.Main.immediate) {//Dispatchers.Main 区别: 1、2打印顺序
            Log.d(TAG, "dispatcher: immediate")//1
        }
        Log.d(TAG, "dispatcher: outer")//2
    }

    /*
协程中启动的协程, 就是子协程. GlobalScope 除外; 新协程的Job, 也是子Job
父协程取消时(主动取消或异常取消), 递归取消所有子协程, 及子子协程
父协程会等待子协程全部执行完毕才会结束

当一个协程由于异常而运行失败时:
取消它自己的子级；
取消它自己；
将异常传播并传递给它的父级。
异常会到达层级的根部，而且当前 CoroutineScope 所启动的所有协程都会被取消。
     */
    private fun exception() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "exception exceptionHandler: ${exception.message}")
        }
        //CoroutineExceptionHandler【全局】捕获异常：必须加到根协程作用域上才起作用
        //无法在CoroutineExceptionHandler中从异常中恢复。当处理程序被调用时，协程已经完成了相应的异常。通常，该处理程序用于记录异常、显示某种错误消息、终止和/或重新启动应用程序。
        launch(exceptionHandler) {//根协程
//            val async = async(exceptionHandler) { 1 / 0 }//加到此处还是会崩溃
            val async = async { 1 / 0 }//不崩溃，但异常产生后 整个作用域的代码也立即结束执行
            async.await()
            Log.d(TAG, "exception: exceptionHandler after")
        }/*.invokeOnCompletion {
            Log.d(TAG, "exception complete: $it")
        }*/

        /*   //正常情况下 try catch 就能捕获异常，且异常后，协程后面的代码中止
           launch { // 根协程为 launch
               Log.d(TAG, "exception: scope start")
               try {
                   throw Exception("scope ex")
               } catch (e: Exception) {
                   Log.d(TAG, "exception: scope ${e.message}")
               }
               Log.d(TAG, "exception: scope end")
           }*/
        /*
                val async = async { //根协程为 async
                    Log.d(TAG, "exception: async")
                    throw Exception("async ex")
                }
                launch {
                    try {
                        //async 被用作【根协程】时，它的结果和异常会包装在 返回值 Deferred.await() 中;因此, try{..}catch {..} 需要包裹 await(); 而包裹 async{..} 是没有意义的.
                        //如果async作为一个子协程时，那么异常并不会等到调用await时抛出，而是立刻抛出异常
                        async.await()
                    } catch (e: Exception) {
                        Log.d(TAG, "exception: async catch $e")
                    }
                }*/

        /*    //子协程的异常无法上抛给父协程，所以下面这段直接崩溃.
            //想想也对, 协程块代码始终是要分发给线程去做(或者post 一个runnable里执行). try catch 又不是包在代码块里面.
            //这么理解，你能直接在一个线程里try catch住另一个线程的异常吗？async也是这样的道理
            launch { //根协程
                try {
                    val async = async { 1 / 0 } //子协程
                    async.await()
                } catch (e: Exception) {
                    Log.e(TAG, "exception: child $e")
                }
            }*/
//        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//            Log.d(TAG, "CoroutineExceptionHandler exception : ${exception.message}")
//        }
        /*        //CoroutineExceptionHandler【全局】捕获异常：必须加到根协程作用域上才起作用
                //无法在CoroutineExceptionHandler中从异常中恢复。当处理程序被调用时，协程已经完成了相应的异常。通常，该处理程序用于记录异常、显示某种错误消息、终止和/或重新启动应用程序。
                launch(exceptionHandler) {//根协程
        //            val async = async(exceptionHandler) { 1 / 0 }//加到此处还是会崩溃
                    val async = async() { 1 / 0 }//不崩溃，但异常产生后 整个作用域的代码也立即结束执行
                    async.await()
                    Log.d(TAG, "exception: exceptionHandler after")
                }*/

        /* //使用 SupervisorJob、supervisorScope 时，子协程的运行失败不会影响到其他子协程。也不会传播异常给它的父级，它会让子协程自己处理异常
        //只能作用一层, 它的直接子协程不会向上传递取消. 但子协程的内部还是普通的双向传递模式;
         val supervisorJob = SupervisorJob() //取消单向传递的 job
         // runBlocking,coroutineScope 中创建的协程不是根协程
         launch(coroutineContext + supervisorJob + exceptionHandler) {
             supervisorScope {
                 launch {    //兄弟协程
                     delay(100)
                     Log.d(TAG, "exception: supervisorJob")// 还是会打印
                 }
                 launch { throw AssertionError("The second child is cancelled") }
                 Log.d(TAG, "exception: supervisorJob after")// 还是会打印
             }
             Log.d(TAG, "exception: supervisorJob end")
         }
         // runBlocking,coroutineScope 中创建的协程不是根协程
         launch(exceptionHandler) {
             supervisorScope {
                 launch {    //兄弟协程
                     delay(100)
                     Log.d(TAG, "exception: supervisorJob")// 还是会打印
                 }
                 launch { throw AssertionError("The second child is cancelled") }
                 Log.d(TAG, "exception: supervisorJob after")// 还是会打印
             }
             Log.d(TAG, "exception: supervisorJob end")
         }*/
    }

    /**
     * In Kotlin Coroutines, a cancelled job cannot be restarted . You need to create a new job.
     */
    private fun cancelJob() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            Log.d(TAG, "cancelJob: before cancel")
        }
        handler.postDelayed(1000) {
            Log.d(TAG, "cancelJob")
            scope.cancel()
        }
        handler.postDelayed(2000) {
            scope.launch {
                Log.d(TAG, "cancelJob: after cancel")
            }
        }

    }

    private var cancelableJob: Job? = null
    private var n = 0
    private fun cancelKnow() {
        cancelableJob?.cancel("取消了")
        n = 0
        cancelableJob = lifecycleScope.launch(Dispatchers.IO) {
//            try {
            while (true) {
//                while (isActive) { //最好用这个来判断
                delay(2000) //如果用SystemClock则无法取消。 取消原理
//                SystemClock.sleep(500)
                Log.d(TAG, "scope: loop ${n++}")
            }
//            } catch (e: Exception) {//会抛出取消异常，不捕获也不会崩溃
//                Log.d(TAG, "cancel: ${e.message}")
//            }

        }

//        window.decorView.postDelayed({ cancelableJob?.cancel() }, 4000)
    }

    /**
     * 注意打印顺序
     */
    private fun scope() {
        val scope = CoroutineScope(Dispatchers.Main)
        /*        scope.launch {
            launch {
                Log.d(TAG_L, "scope: sub launch")//3
            }
            Log.d(TAG_L, "scope: launch")//2
        }*/

        scope.launch {
            val async = async {
                delay(400)
                Log.d(TAG, "scope: async")
            }//3
            //async.await() 放开此行时 1 3 2，关闭此行时 1 2 3
            Log.d(TAG, "scope: after async")//2
        }
        Log.d(TAG, "scope: ") //1
    }

    @Composable
    fun Case() {
        Column {
            Row {
                Label("mutex") { mutexDeadLock() }
                Label("select") { caseSelect() }
                Label("channel") { caseChannel() }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CaseTheme { Case() } }
    }
}