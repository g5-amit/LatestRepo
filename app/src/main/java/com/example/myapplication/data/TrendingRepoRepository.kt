package com.example.myapplication.data

import com.example.myapplication.network.util.Resource
import com.example.myapplication.network.api.response.GitHubRepo
import com.example.myapplication.data.remote.IRepoData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class TrendingRepoRepository @Inject constructor(
    @com.example.myapplication.di.scope.RepoRemoteDataSource
    private val remoteSource : IRepoData,
) : BaseRepoRepository {

    override suspend fun getTrendingRepoList(): Resource<List<GitHubRepo>> {
        return remoteSource.getTrendingRepoList()
    }
}