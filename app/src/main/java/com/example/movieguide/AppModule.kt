package com.example.movieguide

import com.example.network.tvmaze.TvMazeApi
import com.example.network.tvmaze.retrofit.TvMazeRetrofitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): TvMazeApi {
        return TvMazeRetrofitImpl()
    }
}