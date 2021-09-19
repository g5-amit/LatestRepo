package com.example.myapplication.network.api

import com.example.myapplication.network.api.response.GitHubRepo
import com.example.myapplication.utility.Constant
import retrofit2.Response
import retrofit2.http.GET

/**
 * All Api calls interfaces for Retrofit, will be defined here to deal with Network api
 */
interface RemoteApiService {
    @GET(Constant.REMOTE_TRENDING_REPO_URI)
    suspend fun getRemoteTrendingRepos(): Response<List<GitHubRepo>>
}