package com.example.myapplication.di.scope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RepoRemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RepoLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TrendingRepository


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher