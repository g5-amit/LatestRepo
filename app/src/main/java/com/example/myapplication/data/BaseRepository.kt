package com.example.myapplication.data

import com.example.myapplication.network.util.Resource
import com.example.myapplication.network.api.response.GitHubRepo

/**
* For ViewModel injection TrendingRepository and FakeRepository will be injected from fragment using DI
* */
interface BaseRepoRepository {
    suspend fun getTrendingRepoList(): Resource<List<GitHubRepo>>
    suspend fun getRefreshRepoList(): Resource<List<GitHubRepo>>
}