package com.example.bingewatcher

import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.retrofit.TvMazeRetrofitImpl
import com.example.repository.SearchRepository
import com.example.repository.SearchRepositoryImpl
import com.example.repository.ShowRepository
import com.example.repository.ShowRepositoryImpl
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
    fun provideApi(): TvMazeRemoteDataSource {
        return TvMazeRetrofitImpl()
    }

    @Provides
    @Singleton
    fun provideShowRepository(remoteDataSource: TvMazeRemoteDataSource): ShowRepository {
        return ShowRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(remoteDataSource: TvMazeRemoteDataSource): SearchRepository {
        return SearchRepositoryImpl(remoteDataSource)
    }
}