package com.ccino.demo.jetpack.paging

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.ccino.demo.databinding.ActivityPagingBinding
import kotlinx.coroutines.launch

private const val TAG = "PagingActivity"

class PagingActivity : AppCompatActivity() {

    private val mAdapter = RepositoryAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this)[PageVM::class.java]
        binding.recyclerView.adapter = mAdapter
        lifecycleScope.launch {
            mainViewModel.getPagingData().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }
        mAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    Log.d(TAG, "LoadState: ${mAdapter.itemCount}")
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.refreshLayout.isRefreshing = false
                }

                is LoadState.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }

                is LoadState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }
}
