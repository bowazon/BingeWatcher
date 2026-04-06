package com.bowazon.bingewatcher.core.designsystem

import android.R.attr.scaleY
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
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml

@Composable
fun BingeWatcherEpisodeSmallCard(
    modifier: Modifier = Modifier,
    name: String,
    pictureUrl: String,
    date: String,
    rating: String,
    description: String? = null,
    isEpiWatched: Boolean,
    isEpiLoading: Boolean = false,
    onToggle: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var showDescription by remember { mutableStateOf(false) }
    // TODO we need to pass Painter in designsystem
    val imageLoader = rememberAsyncImagePainter(
        model = pictureUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

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
                    painter = if (!isError) {
                        imageLoader
                    } else {
                        painterResource(R.drawable.ic_launcher_foreground)
                    },
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
    isWatched: Boolean,
    isLoading: Boolean = false,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
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
