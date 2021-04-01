package com.blogspot.soyamr.newsplusplus.data.di

import com.blogspot.soyamr.newsplusplus.data.RepositoryImpl
import com.blogspot.soyamr.newsplusplus.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun getRepo(repositoryImpl: RepositoryImpl): Repository
}