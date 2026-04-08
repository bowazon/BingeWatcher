package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme
import com.bowazon.bingewatcher.core.designsystem.utils.Rating
import com.bowazon.bingewatcher.core.designsystem.utils.RatingWithStar
import kotlin.text.isNotEmpty

@Composable
private fun BaseTopBadge(
    modifier: Modifier = Modifier,
    largeText: String = "", // TODO move everything to null; too bad it means we should rewrite UI models
    smallText: String = "",
    rightText: String = "",
    rating: Rating = Rating("")
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = largeText,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = smallText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(verticalAlignment = Alignment.Top,
            modifier = Modifier.align(Alignment.TopEnd)) {
            if (rating.rating.isNotEmpty()) {
                RatingWithStar(rating.rating)
            }
            if(rightText.isNotEmpty()) {
                Text(
                    text = rightText,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun BingeWatcherPersonTopBadge(
    modifier: Modifier = Modifier,
    name: String,
    credits: String,
    born: String,
) {
    BaseTopBadge(
        modifier = modifier,
        largeText = name,
        smallText = credits,
        rightText = if (born.isNotEmpty()) (stringResource(R.string.born) + born) else ""
    )
}



@Composable
fun BingeWatcherShowTopBadge(
    modifier: Modifier = Modifier,
    name: String,
    premiered: String,
    rating: String
) {
    BaseTopBadge(
        modifier = modifier,
        largeText = name,
        smallText = premiered,
        rating = Rating(rating)
    )
}

@Composable
fun BingeWatcherShowBottomBadge(
    modifier: Modifier = Modifier,
    genres: String,
    description: String,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = genres,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = AnnotatedString.Companion.fromHtml(description),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    widthDp = 412,
    heightDp = 100,
)
@Composable
fun BingeWatcherPersonTopBadgePreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider( // TODO cringe
            LocalContentColor provides Color.White
        ) {
            BingeWatcherPersonTopBadge(
                name = "Bryan Cranston",
                credits = "Actor · Director · Producer",
                born = "Mar 7, 1956",
            )
        }
        //Text(
        //    text = "DEBUG",
        //    color = Color.Red
        //)
    }
}

@Preview(
    widthDp = 412,
    heightDp = 100,
)
@Composable
fun BingeWatcherShowTopBadgePreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherShowTopBadge(
                name = "Breaking Bad",
                premiered = "2008 ",
                rating = "9.2",
            )
        }
    }
}

@Preview(
    widthDp = 412,
    heightDp = 100,
)
@Composable
fun BingeWatcherShowBottomBadgePreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherShowBottomBadge(
                genres = "Drama/Crime/Thriller",
                description = "Breaking Bad follows protagonist Walter White, " +
                        "a chemistry teacher who lives in New Mexico with his wife " +
                        "and teenage son who has cerebral palsy. White is diagnosed with "
            )
        }
    }
}