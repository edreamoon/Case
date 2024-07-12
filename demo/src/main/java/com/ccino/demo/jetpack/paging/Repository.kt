package com.ccino.demo.jetpack.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 15

object Repository {

    fun getGithubPagingData(): Flow<PagingData<RepositoryItem>> {
        return Pager(
            /**
             * initialLoadSize：第一页加载的条数，默认是 pageSize*3
             * prefetchDistance：倒数第几个时加载下页
             */
            config = PagingConfig(PAGE_SIZE, enablePlaceholders = false, initialLoadSize = PAGE_SIZE, prefetchDistance = 5),
            pagingSourceFactory = { GithubPageSource() }
        ).flow
    }
}