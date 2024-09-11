package com.ccino.demo.kt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.ccino.demo.databinding.ActivityFlowBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

private const val TAG = "FlowActivyL"

/**
 * # stateflow/sharedflow
 * stateflow collect时会把上次的值发送过来
 * sharedFlow collect时则不会把上次的值发送过来
 *
 * # LiveData / StateFlow 不同：
 * 1. StateFlow多次设置相同的值只会回调一次，LiveData则会每次都回调
 * 2. 当 View 变为 STOPPED 状态时，LiveData会自动取消注册，即不会收到值，而从 StateFlow 或任何其他数据流收集数据则不会取消注册使用方。
 * 3. StateFlow 必须有初始值,并且collect会收到初始值，LiveData 不需要。
 *
 * # 对于 StateFlow 在界面销毁的时仍处于活跃状态，有两种解决方法：
 * 1. 使用 ktx 将 Flow 转换为 LiveData。
 * 2. 在界面销毁的时候，手动取消（这很容易被遗忘）,见onDestroy。
 *
 * # ShareFlow / StateFlow 均是热流，Flow 是冷流
 * StateFlow 和 SharedFlow 提供了在 Flow 中使用 LiveData 式更新数据的能力，但是如果要在 UI 层使用，需要注意生命周期的问题。
 * StateFlow 和 SharedFlow 相比，StateFlow 需要提供初始值，SharedFlow 配置灵活，可提供旧数据同步和缓存配置的功能。
 *
 * https://www.jianshu.com/p/0d0ee5fd4931
 * https://juejin.cn/post/6978829247917850654#heading-17(buffer)
 * # sequence/list
 * 1. funList(),无论调没调用result.first()后续都会有map/filter操作，并且执行过程是执行完所有元素的map操作再执行filter操作
 * 2. funSequence(),没调用result.first()，result的map和filter都不会执行，只有result被使用的时候才会执行，而且执行是惰性的，即map取出一个元素交给filter，而不是map对所有元素都处理过户再交给filter，
 *    而且，当满足条件后就不会往下执行，由结果可以看出，没有对Sequence的4进行map和filter操作，因为3已经满足了条件
 * 像List这种实现Iterable接口的集合类，每调用一次函数就会生成一个新的Iterable，下一个函数再基于新的Iterable执行，每次函数调用产生的临时Iterable会导致额外的内存消耗，
 * 而Sequence在整个流程只有一个，且不改变原sequence。因此，Sequence这种数据类型可以在数据量较大或数据量未知的时候，作为流式处理的解决方案.一个走完再走下个流程
 *
 * # flow
 *  Flow 其内部是按照顺序执行的，这一点跟 Sequences 很类似
 * Sequence是同步完成这些操作的，那有没有办法使用异步完成那些map和filter等操作符呢，答案就是Flow
 * 1. Flow也是cold stream,也就是直到你调用terminal operator(比如collect{})，Flow才会执行，而且如果重复调用collect，则调用会得到同样的结果
 * 2. Flow提供了很多操作符，比如map，filter，scan，groupBy等，它们都是cold stream的，我们可以利用这些操作符完成异步代码。
 *
 * ## terminal operator
 * terminal operator 就是仅当你调用它的时候才会去得到结果，和sequence使用的时候才会执行，Rxjava调用subscribe后才会执行类似，
 * Flow中的terminal operator是suspend函数，其他的terminal operator有toList,toSet;first(),reduce(),flod()等
 *
 * ## 取消 Cancellation
 * 在协程作用内你完全不用考虑这些，因为只会在作用域内执行，作用域外会自动取消
 *
 * ## Errors 处理异常
 * ### 捕获异常
 * 同样Flow有类似的方法catch{}，如果你不使用此方法，那你的应用会抛出异常或者崩溃，你可以像之前一样使用try catch或者catch{}来处理错误
 * ### 异常恢复resume
 * 异常时，希望使用默认数据或者完整的备份来恢复数据流，在Flow中可以使用catch{},但是需要在catch{}代码块里使用emit()来一个一个的发送备份数据，甚至如果我们愿意，可以使用emitAll()可以产生一个新的Flow，
 *
 * # 上下文切换[flowOn]
 * 1. 默认情况下Flow数据会运行在调用者的上下文(线程)中。
 * 2. 使用flowOn()来改变上游的上下文, flowOn 仅影响操作符之前没有自己上下文的操作符。
 * 3. 根据（2）, 即使中间使用 flowOn 切换上下文，collect 执行的线程还是在 flow 创建的线程中执行的。
 *
 * For example:
 * ```
 * withContext(Dispatchers.Main) {
 *     val singleValue = intFlow // will be executed on IO if context wasn't specified before
 *         .map { ... } // Will be executed in IO
 *         .flowOn(Dispatchers.IO)
 *         .filter { ... } // Will be executed in Default
 *         .flowOn(Dispatchers.Default)
 *         .single() // Will be executed in the Main
 * }
 * ```
 *
 * # StateFlow
 * StateFlow只有值变化后才会释放新的值，和distinctUntilChanged类似
 * collect对于它不是必需的，StateFlow创建的时候就能开始释放值
 *
 * # [toLiveData]
 * # [flowLifecycle]
 * - 会使用 flow 变成热流，所以注意代码执行顺序
 * - 当生命周期反复时，flow 也会反复执行
 * # [callback]
 * - callbackFlow：将基于回调的 API 转换为数据流
 * - 使用 awaitClose 来保持流运行，否则在块完成时通道将立即关闭。
 * - awaitClose 参数在流消费者取消流收集cancel()或基于回调的 API 手动调用 SendChannel.close() 时调用或外部的协程被取消，通常用于在完成后清理资源。
 * # 操作符[]
 */

class FlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        binding.lifecycleView.setOnClickListener { callback() }

    }


    private fun textWatcherFlow(): Flow<String> = callbackFlow {
        binding.root.postDelayed({
            for (i in 0..20) {
                trySend(i.toString())
            }
            close()
        }, 2000)

        awaitClose { Log.d(TAG, "textWatcherFlow: awaitClose") } //会一直监听
    }.buffer(Channel.CONFLATED)

    private fun mide(): Flow<String> {
        return textWatcherFlow().onEach {
            Log.d(TAG, "mide: $it")
        }
    }

    private fun callback() {
        lifecycleScope.launch {
            mide().collect {
                Log.d(TAG, "callback: collect $it")
            }
        }
    }
}
