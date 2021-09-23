package com.example.myapplication.di

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageModule {
    @Singleton
    @Provides
    fun provideImageLib(context: Context) : Picasso{
        return Picasso.Builder(context)
            .downloader(OkHttp3Downloader(context))
            .build()
    }
}