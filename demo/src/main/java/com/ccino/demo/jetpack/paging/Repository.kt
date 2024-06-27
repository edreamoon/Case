package com.ccino.demo.jetpack.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 25

object Repository {

    fun getGithubPagingData(): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GithubPageSource() }
        ).flow
    }
}