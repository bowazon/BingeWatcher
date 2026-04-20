package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme
import com.bowazon.bingewatcher.core.designsystem.utils.ToggleState
import com.bowazon.bingewatcher.core.designsystem.utils.Rating
import com.bowazon.bingewatcher.core.designsystem.utils.RatingWithStar
import com.bowazon.bingewatcher.core.designsystem.utils.isNotEmpty
import kotlin.text.isNotEmpty

val MEDIUM_HORIZONTAL_CARD_IMAGE_HEIGHT = 150.dp
val MEDIUM_CARD_PADDING = 16.dp

@Composable
private fun BaseMediumHorizontalCard(
    modifier: Modifier = Modifier,
    name: String,
    picturePainter: Painter,

    // I decided to use "" instead of nulls, because I still want
    // elements to present on card and take space if some data came
    // as null.
    text1: String = "",
    text2: String = "",
    text3: String = "",
    rating: Rating = Rating(""),

    toggleState: ToggleState? = null,
    onToggle: (() -> Unit)? = null,
    imageModifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Box(
        modifier
        .fillMaxWidth()
        .padding(MEDIUM_CARD_PADDING)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Image(
                modifier = imageModifier,//.size(height = height, width = width),
                painter = picturePainter,
                contentDescription = null,
                contentScale = contentScale
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(modifier = Modifier.padding(end = 40.dp),
                    text = name,
                    maxLines = 3,
                    style = MaterialTheme.typography.titleLarge)
                if (text1.isNotEmpty()) {
                    Text(
                        text = text1,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (text2.isNotEmpty()) {
                    Text(
                        text = text2,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline)
                }
                if (text3.isNotEmpty()) {
                    Text(
                        text = text3,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd),
        ) {
            if (rating.isNotEmpty()) {
                RatingWithStar(rating)
            }

            if (toggleState != null && onToggle != null) {
                WatchedToggle(
                    isWatched = toggleState.isChecked,
                    isLoading = toggleState.isLoading,
                    onToggle = onToggle
                )
            }
        }
    }
}

@Composable
fun BingeWatcherMediumShowCard(
    modifier: Modifier = Modifier,
    showName: String,
    picturePainter: Painter,
    date: String,
    genre: String,
    rating: String,
    additional: String = "",
    onClick: (() -> Unit)
) {
    BaseMediumHorizontalCard(
        modifier = modifier.clickable {
            onClick()
        },
        name = showName,
        picturePainter = picturePainter,
        text1 = additional,
        text2 = date,
        text3 = genre,
        rating = Rating(rating),
        //width = 110.dp
        imageModifier = Modifier.size(
            height = MEDIUM_HORIZONTAL_CARD_IMAGE_HEIGHT,
            width = 110.dp
        )
    )
}

@Composable
fun BingeWatcherMediumEpisodeCard(
    modifier: Modifier = Modifier,
    episodeName: String,
    picturePainter: Painter,
    date: String,
    rating: String,
    description: String? = null,
    toggleState: ToggleState? = null,
    onToggle: (() -> Unit)? = null
) {
    var showDescription by remember { mutableStateOf(
        toggleState?.isChecked ?: false) }

    Column(modifier = modifier
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = MaterialTheme.shapes.medium
        )
        .padding(8.dp)
        .clickable { showDescription = !showDescription }) {

        BaseMediumHorizontalCard(
            modifier = modifier,
            name = episodeName,
            picturePainter = picturePainter,
            text1 = date,
            rating = Rating(rating),
            toggleState = toggleState,
            onToggle = onToggle,
            imageModifier = Modifier.size(
                height = MEDIUM_HORIZONTAL_CARD_IMAGE_HEIGHT,
                width = MEDIUM_HORIZONTAL_CARD_IMAGE_HEIGHT
            )
        )

        if (showDescription && description != null) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                text = AnnotatedString.Companion.fromHtml(description),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BingeWatcherMediumPersonCard(
    modifier: Modifier = Modifier,
    name: String,
    picturePainter: Painter,
    role: String,
    onClick: () -> Unit
) {
    BaseMediumHorizontalCard(
        modifier = modifier.clickable{
            onClick()
        },
        name = name,
        picturePainter = picturePainter,
        text1 = role,
        imageModifier = Modifier
            .clip(CircleShape)
            .size(100.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun WatchedToggle(
    modifier: Modifier = Modifier,
    isWatched: Boolean,
    isLoading: Boolean = false,
    onToggle: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val animatedColor by animateColorAsState(
        targetValue = if (isWatched)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outline,
        label = "colorAnim"
    )

    val scale by animateFloatAsState(
        targetValue = if (isWatched) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnim"
    )

    val pressed by interactionSource.collectIsPressedAsState()

    val pressScale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else scale,
        label = "pressScale"
    )

    Box(
        modifier = modifier
            .size(40.dp)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }.border(
                width = 2.dp,
                color = if (isWatched)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(animatedColor)
            .clickable(
                interactionSource = interactionSource,
                onClick = onToggle
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
        } else if (isWatched) {
            AnimatedVisibility(
                visible = isWatched,
                enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)) +
                        fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.description_watched),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Preview(
    widthDp = 412,
    heightDp = 100,
)
@Composable
fun BingeWatcherMediumShowCardPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherMediumShowCard(
                showName = "Breaking Bad",
                picturePainter = painterResource(R.drawable.breaking_bad_official),
                date = "2008",
                genre = "Drama/Crime/Thriller",
                rating = "9.2",
                onClick = {}
            )
        }
    }
}

@Preview(
    widthDp = 412,
    heightDp = 400,
)
@Composable
fun BingeWatcherMediumEpisodeCardPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherMediumEpisodeCard(
                episodeName = "Pilot",
                picturePainter = painterResource(R.drawable.breaking_bad_episode_pilot),
                date = "2008",
                rating = "8.2",
                description = "A high-school chemistry teacher (Bryan Cranston) " +
                        "is diagnosed with a deadly cancer, so he puts his expertise " +
                        "to use and teams with an ex-student (Aaron Paul) to manufacture " +
                        "top-grade crystal meth in hopes of providing for his family after" +
                        " he's gone.",
                toggleState = ToggleState(
                    isChecked = true,
                    isLoading = false,
                ),
                onToggle = {}
            )
        }
    }
}