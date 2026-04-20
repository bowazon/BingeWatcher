package com.bowazon.bingewatcher.core.database.models

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromGenresList(value: List<String>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toGenresList(value: String): List<String> {
        return json.decodeFromString(value)
    }
}