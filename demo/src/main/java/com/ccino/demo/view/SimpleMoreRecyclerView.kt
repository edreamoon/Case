package com.ccino.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SimpleMoreRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {
    private var mWrapAdapter: WrapAdapter? = null
    private val mDataObserver: AdapterDataObserver = DataObserver()
    private var hasMore = true

    override fun setAdapter(adapter: Adapter<in ViewHolder>?) {
        adapter?.let {
            mWrapAdapter = WrapAdapter(it)
            super.setAdapter(mWrapAdapter)

            it.registerAdapterDataObserver(mDataObserver)
            mDataObserver.onChanged()
        }
    }

    override fun getAdapter() = mWrapAdapter?.oriAdapter


    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (mWrapAdapter != null) {
            if (layout is GridLayoutManager) {
                mWrapAdapter?.resetGridSpanSize(layout)
            }
        }
    }

    fun setNoMore() {
        hasMore = false
        mWrapAdapter?.oriAdapter?.notifyItemInserted(mWrapAdapter!!.itemCount)
    }

    private inner class WrapAdapter(private val adapter: Adapter<ViewHolder>) : Adapter<ViewHolder>() {
        val oriAdapter: Adapter<ViewHolder>
            get() = adapter

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return if (viewType == TYPE_FOOTER) {
                FooterHolder(FooterView(parent.context))
            } else adapter.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ")
            if (holder !is FooterHolder) {
                val itemCount = adapter.itemCount
                if (position < itemCount) {
                    adapter.onBindViewHolder(holder, position)
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
            if (payloads.isEmpty()) {
                adapter.onBindViewHolder(holder, position)
            } else {
                adapter.onBindViewHolder(holder, position, payloads)
            }
        }

        inner class FooterHolder(itemView: FooterView) : ViewHolder(itemView)

        override fun getItemViewType(position: Int): Int {
            if (isFooter(position)) {
                return TYPE_FOOTER
            }
            if (position < adapter.itemCount) {
                val type = adapter.getItemViewType(position)
                check(!viewTypeConflict(type)) { "itemViewType in adapter conflict with MoreRecyclerView's footer type" }
                return type
            }
            return super.getItemViewType(position)
        }

        override fun getItemCount(): Int {
            return adapter.itemCount
        }

        override fun getItemId(position: Int): Long {
            if (position < adapter.itemCount) {
                if (position < adapter.itemCount) {
                    return adapter.getItemId(position)
                }
            }
            return super.getItemId(position)
        }

        fun isFooter(position: Int): Boolean {
            return !hasMore && position == itemCount - 1
        }

        private fun viewTypeConflict(viewType: Int): Boolean {
            return viewType == TYPE_FOOTER
        }

        fun resetGridSpanSize(manager: GridLayoutManager) {
            val oriLookup = manager.spanSizeLookup
            val newLookup: SpanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isFooter(position)) manager.spanCount else oriLookup.getSpanSize(position)
                }
            }
            manager.spanSizeLookup = newLookup
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            super.onAttachedToRecyclerView(recyclerView)
            val manager = recyclerView.layoutManager
            if (manager is GridLayoutManager) {
                resetGridSpanSize(manager)
            }
            adapter.onAttachedToRecyclerView(recyclerView)
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView)
        }

        override fun onViewAttachedToWindow(holder: ViewHolder) {
            super.onViewAttachedToWindow(holder)
            val lp = holder.itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams && isFooter(holder.layoutPosition)) {
                lp.isFullSpan = true
            }
            adapter.onViewAttachedToWindow(holder)
        }

        override fun onViewDetachedFromWindow(holder: ViewHolder) {
            adapter.onViewDetachedFromWindow(holder)
        }

        override fun onViewRecycled(holder: ViewHolder) {
            adapter.onViewRecycled(holder)
        }

        override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
            return adapter.onFailedToRecycleView(holder)
        }

        override fun unregisterAdapterDataObserver(observer: AdapterDataObserver) {
            adapter.unregisterAdapterDataObserver(observer)
        }

        override fun registerAdapterDataObserver(observer: AdapterDataObserver) {
            adapter.registerAdapterDataObserver(observer)
        }

    }

    private inner class DataObserver : AdapterDataObserver() {
        override fun onChanged() {
            mWrapAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Log.d("DataObserver", "onItemRangeInserted: ")
            mWrapAdapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            Log.d("DataObserver", "onItemRangeChanged: ")
            mWrapAdapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            Log.d("DataObserver", "onItemRangeChanged: ")
            mWrapAdapter?.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            Log.d("DataObserver", "onItemRangeRemoved: ")
            mWrapAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            Log.d("DataObserver", "onItemRangeMoved: ")
            mWrapAdapter?.notifyItemMoved(fromPosition, toPosition)
        }
    }

    companion object {
        private const val TYPE_FOOTER = 122321
        private const val TAG = "MoreRecyclerView"
    }
}