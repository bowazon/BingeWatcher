package com.bowazon.bingewatcher

import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.retrofit.TvMazeRetrofitImpl
import com.bowazon.bingewatcher.core.repository.PersonRepository
import com.bowazon.bingewatcher.core.repository.PersonRepositoryImpl
import com.bowazon.bingewatcher.core.repository.SearchRepository
import com.bowazon.bingewatcher.core.repository.SearchRepositoryImpl
import com.bowazon.bingewatcher.core.repository.SeasonRepository
import com.bowazon.bingewatcher.core.repository.SeasonRepositoryImpl
import com.bowazon.bingewatcher.core.repository.ShowRepository
import com.bowazon.bingewatcher.core.repository.ShowRepositoryImpl
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

    // TODO binds for my repositories???
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

    @Provides
    @Singleton
    fun providePersonRepository(remoteDataSource: TvMazeRemoteDataSource): PersonRepository {
        return PersonRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSeasonRepository(remoteDataSource: TvMazeRemoteDataSource): SeasonRepository {
        return SeasonRepositoryImpl(remoteDataSource)
    }
}