package com.example.myapplication.data.remote

import android.content.Context
import com.example.myapplication.network.util.BaseDataSource
import com.example.myapplication.network.util.Resource
import com.example.myapplication.network.api.RemoteApiService
import com.example.myapplication.network.api.response.GitHubRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RepoRemoteSourceImpl @Inject constructor(
    private val repoApiService: RemoteApiService,
    @ApplicationContext appContext: Context
) : BaseDataSource(appContext), IRepoData {

    override suspend fun getTrendingRepoList(): Resource<List<GitHubRepo>> {
        return getResult {  repoApiService.getRemoteTrendingRepos() }
    }

}