package com.bowazon.bingewatcher.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bowazon.bingewatcher.core.database.dao.ShowDao
import com.bowazon.bingewatcher.core.database.models.ShowDbo

@Database(entities = [ShowDbo::class], version = 1)
abstract class BingeWatcherDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
}

fun BingeWatcherDatabase(applicationContext: Context): BingeWatcherDatabase {
    val bingeWatcherDatabase =
        Room.databaseBuilder(
            applicationContext.applicationContext,
            BingeWatcherDatabase::class.java,
            "BingeWatcherDatabase"
        ).build()
    return bingeWatcherDatabase
}