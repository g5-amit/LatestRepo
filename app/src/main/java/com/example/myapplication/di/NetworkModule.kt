package com.example.myapplication.di

import com.example.myapplication.network.api.RemoteApiService
import com.example.myapplication.utility.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * All Network Api dependency will be injected from Network Module
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val READ_TIMEOUT = 15L
        const val WRITE_TIMEOUT = 60L
        const val CONNECTION_TIMEOUT = 15L
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(Constant.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteApiService = retrofit.create(
        RemoteApiService::class.java
    )

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
        return clientBuilder.build()
    }

}