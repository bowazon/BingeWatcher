package com.bowazon.bingewatcher.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeasonDbo(
    @PrimaryKey val id: Int,
)