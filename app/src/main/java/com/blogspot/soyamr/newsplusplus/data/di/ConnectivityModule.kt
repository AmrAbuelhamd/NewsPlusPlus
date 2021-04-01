package com.blogspot.soyamr.newsplusplus.data.di

import com.blogspot.soyamr.newsplusplus.data.util.Connectivity
import com.blogspot.soyamr.newsplusplus.data.util.ConnectivityImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {
    @Binds
    @Singleton
    abstract fun getConnectivityImpl(connectivityImpl: ConnectivityImpl): Connectivity
}