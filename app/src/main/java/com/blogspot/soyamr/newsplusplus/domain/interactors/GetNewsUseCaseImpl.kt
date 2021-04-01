package com.blogspot.soyamr.newsplusplus.domain.interactors

import androidx.paging.PagingData
import com.blogspot.soyamr.newsplusplus.domain.Repository
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCaseImpl @Inject constructor(private val repository: Repository) :
    GetNewsUseCase {
    override fun invoke(): Flow<PagingData<Article>> = repository.getNews()
}