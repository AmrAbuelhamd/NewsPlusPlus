package com.blogspot.soyamr.newsplusplus.data

import androidx.paging.*
import com.blogspot.soyamr.newsplusplus.data.db.dao.ArticleDao
import com.blogspot.soyamr.newsplusplus.data.db.dao.RemoteKeysDao
import com.blogspot.soyamr.newsplusplus.data.network.NewsApi
import com.blogspot.soyamr.newsplusplus.data.util.Connectivity
import com.blogspot.soyamr.newsplusplus.domain.Repository
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val keysDao: RemoteKeysDao,
    private val api: NewsApi,
    private val connectivity: Connectivity
) :
    Repository {
    override fun getNews(): Flow<PagingData<Article>> {

        val pagingSourceFactory = { articleDao.getAll() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            remoteMediator = NewsRemoteMediator(articleDao, keysDao, api),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it -> it.map { it.toDomain() } }
    }

}
