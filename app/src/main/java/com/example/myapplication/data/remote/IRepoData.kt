package com.example.myapplication.data.remote

import com.example.myapplication.network.util.Resource
import com.example.myapplication.network.api.response.GitHubRepo

interface IRepoData {
    suspend fun getTrendingRepoList(): Resource<List<GitHubRepo>>
}