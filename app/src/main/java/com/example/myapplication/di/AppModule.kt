package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.network.api.RemoteApiService
import com.example.myapplication.data.remote.IRepoData
import com.example.myapplication.data.remote.RepoRemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @com.example.myapplication.di.scope.RepoRemoteDataSource
    @Singleton
    @Provides
    fun provideRepoRemoteDataSource(remoteApiService: RemoteApiService, @ApplicationContext appContext: Context
    ):IRepoData
            = RepoRemoteSourceImpl(remoteApiService, appContext)



//    @Singleton
//    @Provides
//    fun provideRepository(
//        @com.example.myapplication.di.scope.RepoRemoteDataSource
//        remoteDataSource: IRepoData,
//
//    ) =
//        TrendingRepoRepository(remoteDataSource)
}