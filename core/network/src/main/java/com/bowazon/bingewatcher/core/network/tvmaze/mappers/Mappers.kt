package com.bowazon.bingewatcher.core.network.tvmaze.mappers

import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.Person
import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewCredits
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeActor
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCastCredit
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCrewCredit
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCrewMember
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeEpisode
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazePerson
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeSeason
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeShow
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeShowImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import kotlin.String
import kotlin.text.substringBefore

const val IMDB_ADDRESS = "https://www.imdb.com/title/"

fun TvMazeShow.toShowSummary(): ShowSummary {
    return ShowSummary(
        name = name,
        premiered = premiered?.substringBefore("-") ?: "",
        image = image?.medium  ?: "" ,
        genres = genres ?: listOf(""),
        rating = rating?.average
    )
}

fun TvMazeShow.toShowDetailed(): ShowDetailed {
    return ShowDetailed(
        id = id,
        summary = toShowSummary(),
        ended = ended?.substringBefore("-") ?: "", // empty if still active
        officialSite = officialSite ?: "",
        imdb = IMDB_ADDRESS + (externals?.imdb ?: ""),
        largeImage = image?.original ?: "",
        description = summary ?: ""
    )
}

fun TvMazeActor.toActor(): ActorSummary {
    return ActorSummary(
        id = person?.id?.let { it.toInt() } ?: 0,
        name = person?.name ?: "",
        image = person?.image?.medium ?: "",
        characterName = character?.name ?: ""
    )
}

fun TvMazeActor.toCrew() : CrewSummary {
    return CrewSummary(
        id = person?.id?.toInt() ?: 0,
        name = person?.name ?: "",
        image = person?.image?.medium ?: "",
        role = "Actor"
    )
}

fun TvMazeSeason.toSeason(showName: String): Season {
    return Season (
        id = id.toInt(),
        showName = showName,
        seasonNumber = number.toInt(),
        episodesAmount = episodeOrder?.toInt() ?: 0,
        image = image?.medium ?: "",
        premiereDate = premiereDate?.substringBefore("-") ?: ""
    )
}

fun TvMazeShowImage.toImageUrl(): String? {
    return resolutions?.original?.url
}

fun TvMazePerson.toPerson(): Person {
    return Person(
        id = id.toInt(),
        name = name,
        image = image?.medium ?: "",
        country = country?.name ?: "",
        birthday = formatDate(birthday ?: ""),
        gender = gender ?: "",
    )
}

private val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)

fun formatDate(input: String?): String {
    if (input.isNullOrBlank()) return ""

    return try {
        LocalDate.parse(input).format(formatter)
    } catch (e: DateTimeParseException) {
        ""
    }
}

fun TvMazeEpisode.toEpisode(): Episode {
    return Episode(
        id = id.toInt(),
        name = name ?: "",
        season = season?.toInt() ?: 0,
        episode = number?.toInt() ?: 0,
        airdate = formatDate(airdate ?: ""),
        runtime = runtime?.toInt() ?: 0,
        rating = rating?.average,
        image = image?.medium ?: "",
        summary = summary ?: "",
        showName = links?.show?.name ?: ""
    )
}

fun TvMazeCastCredit.toActorCredits(): ActorCredits {
    return ActorCredits(
        showId = embedded?.show?.id ?: 0,
        showName = embedded?.show?.name ?: "",
        showPicture = embedded?.show?.image?.medium ?: "",
        showGenre = embedded?.show?.genres?.joinToString(separator = "/") ?: "",
        showPremiered = embedded?.show?.premiered?.substringBefore("-") ?: "",
        showRating = embedded?.show?.rating?.average,
        character = links.character.name,
    )
}

fun TvMazeCrewCredit.toCrewCredits(): CrewCredits {
    return CrewCredits(
        type = type,
        showId = embedded?.show?.id ?: 0,
        showName = embedded?.show?.name ?: "",
        showPicture = embedded?.show?.image?.medium ?: "",
        showGenre = embedded?.show?.genres?.joinToString(separator = "/") ?: "",
        showPremiered = embedded?.show?.premiered?.substringBefore("-") ?: "",
        showRating = embedded?.show?.rating?.average,
    )
}

fun TvMazeCrewMember.toCrew(): CrewSummary {
    return CrewSummary(
        id = person.id.toInt(),
        name = person.name,
        image = person.image?.medium ?: "",
        role = type
    )
}