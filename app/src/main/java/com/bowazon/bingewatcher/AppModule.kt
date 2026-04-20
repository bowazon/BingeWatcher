package com.bowazon.bingewatcher

import android.content.Context
import com.bowazon.bingewatcher.core.database.BingeWatcherDatabase
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.retrofit.TvMazeRetrofitImpl
import com.bowazon.bingewatcher.core.repository.FullCrewRepository
import com.bowazon.bingewatcher.core.repository.FullCrewRepositoryImpl
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
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideShowRepository(
        remoteDataSource: TvMazeRemoteDataSource,
        database: BingeWatcherDatabase
    ): ShowRepository {
        return ShowRepositoryImpl(remoteDataSource, database)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(remoteDataSource: TvMazeRemoteDataSource): SearchRepository {
        return SearchRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun providePersonRepository(
        remoteDataSource: TvMazeRemoteDataSource,
        database: BingeWatcherDatabase
    ): PersonRepository {
        return PersonRepositoryImpl(remoteDataSource, database)
    }

    @Provides
    @Singleton
    fun provideSeasonRepository(
        remoteDataSource: TvMazeRemoteDataSource,
        database: BingeWatcherDatabase
    ): SeasonRepository {
        return SeasonRepositoryImpl(remoteDataSource, database)
    }

    @Provides
    @Singleton
    fun provideFullCrewRepository(remoteDataSource: TvMazeRemoteDataSource): FullCrewRepository {
        return FullCrewRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : BingeWatcherDatabase {
        return BingeWatcherDatabase(context)
    }
}