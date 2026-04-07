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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.text.isNotEmpty




@Composable
fun BingeWatcherShowSmallCard(
    modifier: Modifier = Modifier,
    showName: String,
    picturePainter: Painter,
    date: String,
    genre: String,
    rating: String,
    additional: String? = null,
) {

    Box(modifier.padding(16.dp)) {
        Row {
            Image(
                modifier = Modifier.size(150.dp),
                painter = picturePainter,
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = showName, color = Color.White)
                additional?.let { Text(text = it, color = Color.White) }
                Text(text = date, color = Color.White)
                Text(text = genre, color = Color.White)
            }
        }

        if (rating.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = rating,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun BingeWatcherEpisodeSmallCard(
    modifier: Modifier = Modifier,
    name: String,
    picturePainter: Painter,
    date: String,
    rating: String,
    description: String? = null,
    isEpiWatched: Boolean,
    isEpiLoading: Boolean = false,
    onToggle: () -> Unit,
) {
    var showDescription by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = MaterialTheme.shapes.medium
        )
        .padding(8.dp)) {
        Box(Modifier.padding(16.dp)) {
            Row {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = picturePainter,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(end = 40.dp),
                        text = name,
                        color = Color.White,
                        maxLines = 3,
                        //modifier = Modifier.basicMarquee()
                    )
                    Text(text = date, color = Color.White)
                }
            }

            if (rating.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = rating,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    WatchedToggle(
                        isWatched = isEpiWatched,
                        isLoading = isEpiLoading,
                        onToggle = onToggle
                    )
                }
            }
        }

        Button(
            onClick = { showDescription = !showDescription },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(if (showDescription) "Hide" else "Description")
        }

        if (showDescription && description != null) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                text = AnnotatedString.Companion.fromHtml(description),
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun WatchedToggle(
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
                    contentDescription = "Watched",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
