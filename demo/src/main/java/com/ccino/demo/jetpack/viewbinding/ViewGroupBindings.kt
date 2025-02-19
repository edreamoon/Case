@file:Suppress("unused")

package com.ware.jetpack.viewbinding

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.ccino.demo.jetpack.viewbinding.EagerViewBindingProperty
import com.ccino.demo.jetpack.viewbinding.LazyViewBindingProperty
import com.ccino.demo.jetpack.viewbinding.LifecycleViewBindingProperty
import com.ccino.demo.jetpack.viewbinding.ViewBindingProperty
import com.ccino.demo.jetpack.viewbinding.requireViewByIdCompat
import com.ware.jetpack.viewbinding.*

@PublishedApi
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class ViewGroupViewBindingProperty<in V : ViewGroup, out T : ViewBinding>(
    viewBinder: (V) -> T
) : LifecycleViewBindingProperty<V, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: V): LifecycleOwner {

        return checkNotNull(thisRef.context as? LifecycleOwner) {
            "Fragment doesn't have view associated with it or the view has been destroyed"
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(lifecycleAware = false, vbFactory)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    lifecycleAware: Boolean,
    crossinline vbFactory: (ViewGroup) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        lifecycleAware -> ViewGroupViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
        else -> LazyViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
    }
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Deprecated("Order of arguments was changed", ReplaceWith("viewBinding(viewBindingRootId, vbFactory)"))
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline vbFactory: (View) -> T,
    @IdRes viewBindingRootId: Int,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, vbFactory)
}

inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return viewBinding(viewBindingRootId, lifecycleAware = false, vbFactory)
}

/**
 * Create new [ViewBinding] associated with the [ViewGroup]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 * @param lifecycleAware Get [LifecycleOwner] from the [ViewGroup][this] using [ViewTreeLifecycleOwner]
 */
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    @IdRes viewBindingRootId: Int,
    lifecycleAware: Boolean,
    crossinline vbFactory: (View) -> T,
): ViewBindingProperty<ViewGroup, T> {
    return when {
        isInEditMode -> EagerViewBindingProperty(vbFactory(this))
        lifecycleAware -> ViewGroupViewBindingProperty { viewGroup -> vbFactory(viewGroup) }
        else -> LazyViewBindingProperty { viewGroup: ViewGroup ->
            vbFactory(viewGroup.requireViewByIdCompat(viewBindingRootId))
        }
    }
}
