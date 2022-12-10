package com.example.order.app.domain.di

import com.example.order.app.domain.usecase.CreateListOfAllItemsDBUseCaseImpl
import com.example.order.app.domain.usecase.CreateListOfAllItemsUseCase
import com.example.order.app.domain.usecase.LoadDataFrom1CCase
import com.example.order.app.domain.usecase.LoadDataFromDBUseCaseImpl
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
        fun mainRepository(localRepository: LocalRepositoryImpl) : LocalRepository
        @Binds
        fun getAllItemsUseCase(curList: CreateListOfAllItemsDBUseCaseImpl) : CreateListOfAllItemsUseCase
        @Binds
        fun loadFromDbUseCase(listFromDB:LoadDataFromDBUseCaseImpl):LoadDataFrom1CCase


    }
}