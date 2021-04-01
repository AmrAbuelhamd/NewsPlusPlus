package com.blogspot.soyamr.newsplusplus.domain.interactors

import androidx.paging.PagingData
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface GetNewsUseCase {
    operator fun invoke(): Flow<PagingData<Article>>
}