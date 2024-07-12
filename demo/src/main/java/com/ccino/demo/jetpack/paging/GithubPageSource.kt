package com.ccino.demo.jetpack.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ccino.demo.jetpack.paging.GithubService.Companion.githubApi

private const val TAG = "GithubPageSource"

class GithubPageSource : PagingSource<Int, RepositoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        Log.d(TAG, "getRefreshKey: $state")
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        Log.d(TAG, "load: $params")
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            Log.d(TAG, "load: pageSize=$pageSize")
            val rspRepository = githubApi.getRepositories(page, pageSize)
            val items = rspRepository.items
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (items.isNotEmpty()) page + 1 else null
            LoadResult.Page(items, preKey, nextKey)
        } catch (e: Exception) {
            Log.d(TAG, "load: ${e.message}")
            LoadResult.Error(e)
        }
    }
}