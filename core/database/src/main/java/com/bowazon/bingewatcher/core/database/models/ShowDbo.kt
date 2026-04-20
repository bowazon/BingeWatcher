package com.bowazon.bingewatcher.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary

@Entity(tableName = "shows")
data class ShowDbo(
    @PrimaryKey val id: Int,
    val name: String,
    val premiered: String,
    val image: String,
    val genres: List<String>,
    val rating: String,
    val ended: String, // empty if still active
    val officialSite: String,
    val imdb: String,
    val largeImage: String,
    val description: String,
)

fun ShowDetailed.toShowDbo() : ShowDbo{
    return ShowDbo(
        id = id,
        name = summary.name,
        premiered = summary.premiered,
        image = summary.image,
        genres = summary.genres,
        rating = summary.rating,
        ended = ended,
        officialSite = officialSite,
        imdb = imdb,
        largeImage = largeImage,
        description = description
    )
}

fun ShowDbo.toShow() : ShowDetailed {
    return ShowDetailed(
        id = id,
        summary = ShowSummary(
            name = name,
            premiered = premiered,
            image = image,
            genres = genres,
            rating = rating,
        ),
        ended = ended,
        officialSite = officialSite,
        imdb = imdb,
        largeImage = largeImage,
        description = description
    )
}