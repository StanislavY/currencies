package com.example.order.app.domain.di

import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

object RepositoriesModules {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoriesModule {

        @Binds
        fun mainRepository(mainRepositoryImpl: LocalRepository) : LocalRepositoryImpl
    }
}