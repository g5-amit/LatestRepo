package com.example.myapplication.usecase

import com.example.myapplication.network.api.response.GitHubRepo
import com.example.myapplication.network.util.Resource
import com.example.myapplication.ui.model.RepoItemUIModel

interface IRepoUseCase {
    suspend fun getTrendingRepoList(): Resource<List<RepoItemUIModel>>
}