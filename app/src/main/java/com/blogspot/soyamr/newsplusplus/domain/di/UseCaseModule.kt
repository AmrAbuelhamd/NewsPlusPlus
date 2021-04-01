package com.blogspot.soyamr.newsplusplus.domain.di

import com.blogspot.soyamr.newsplusplus.domain.interactors.GetNewsUseCase
import com.blogspot.soyamr.newsplusplus.domain.interactors.GetNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindUseCase(getNewsUseCaseImpl: GetNewsUseCaseImpl): GetNewsUseCase

}