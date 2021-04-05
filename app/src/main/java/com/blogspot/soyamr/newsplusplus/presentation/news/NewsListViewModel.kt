package com.blogspot.soyamr.newsplusplus.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.blogspot.soyamr.newsplusplus.domain.interactors.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) :
    ViewModel() {

    val news = getNewsUseCase().cachedIn(viewModelScope)

}
