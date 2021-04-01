package com.blogspot.soyamr.newsplusplus.domain

import androidx.paging.PagingData
import com.blogspot.soyamr.newsplusplus.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNews(): Flow<PagingData<Article>> // fixme return list of articls somehow
    //  https://www.google.com/search?rlz=1C1SQJL_enRU910RU910&sxsrf=ALeKk01tD60XP6Dv7QEfCh1kVaRaHKg_gA:1617298459333&q=Paging+Data+in+repository+in+domain+clean+code&spell=1&sa=X&ved=2ahUKEwjAn6ehyt3vAhWr-yoKHR-nAXsQBSgAegQIARAw
}