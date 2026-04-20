package com.bowazon.bingewatcher.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.bowazon.bingewatcher.core.database.models.Converters
import com.bowazon.bingewatcher.core.database.models.ShowDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao { // TODO rename this and probably all DAOs and DB to FavoriteShowDao
    @Query("SELECT * FROM shows")
    suspend fun getAll(): List<ShowDbo>

    @Query("SELECT * FROM shows")
    fun observeAll(): Flow<List<ShowDbo>>

    @Query("SELECT EXISTS(SELECT 1 FROM shows WHERE id = :id)")
    suspend fun isShowSaved(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM shows WHERE id = :id)")
    fun observeShowSaved(id: Int): Flow<Boolean>

    @Insert
    suspend fun insert(shows: List<ShowDbo>)

    @Delete
    suspend fun remove(shows: List<ShowDbo>)

    @Delete
    suspend fun delete(show: ShowDbo)

    @Query("DELETE FROM shows")
    suspend fun clean()
}