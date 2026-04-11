package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme

val SMALL_PERSON_CARD_SIZE = 100.dp
val SMALL_SEASON_CARD_WIDTH = 120.dp
val SMALL_SEASON_CARD_HEIGHT = 150.dp

@Composable
internal fun BaseScalableCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f
    )

    val alpha by animateFloatAsState(
        targetValue = if (pressed) 0.7f else 1f
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .alpha(alpha)
    ) {
        content()
    }
}

@Composable
fun BingeWatcherSmallPersonCard(
    modifier: Modifier = Modifier,
    personName: String,
    imagePainter: Painter,
    onClick: () -> Unit,
    additionalInfo: String? = null,
    additionalInfoHeight: Dp = 0.dp
) {
    BaseScalableCard(
        modifier,
        onClick
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (additionalInfo != null) {
                Box(modifier = Modifier.heightIn(min = additionalInfoHeight)) {
                    Text(
                        text = additionalInfo,
                        maxLines = 3,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Box(Modifier.size(SMALL_PERSON_CARD_SIZE)) {
                Image(
                    painter = imagePainter,
                    contentDescription = personName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = personName)
        }
    }
}


@Composable
fun BingeWatcherSmallSeasonCard(
    modifier: Modifier = Modifier,
    season: Int,
    episodesAmount: Int,
    imagePainter: Painter,
    premiereDate: String,
    onClick: () -> Unit
) {
    BaseScalableCard(
        modifier,
        onClick
    ) {
        Column(modifier) {
            Text(text = stringResource(R.string.season) + season)
            Box(
                modifier = Modifier
                    .height(SMALL_SEASON_CARD_HEIGHT)
                    .width(SMALL_SEASON_CARD_WIDTH)
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.open_season_card),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (episodesAmount != 0) {
                    Text(
                        text = "E$episodesAmount",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            //.padding(8.dp)
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                    )
                }

                Text(
                    text = premiereDate,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        //.padding(8.dp)
                        .background(
                            Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                )
            }
        }
    }
}

@Composable
fun BingeWatcherSmallImageCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit
) {
    BaseScalableCard(
        modifier,
        onClick
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.description_open_image),
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .height(150.dp)
        )
    }
}


@Preview(
    widthDp = 110,
    heightDp = 110,
)
@Composable
fun BingeWatcherSmallPersonCardPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherSmallPersonCard(
                onClick = {},
                personName = "Bryan Cranston",
                imagePainter = painterResource(R.drawable.bryan_cranston),
            )
        }
    }
}

@Preview(
    widthDp = 130,
    heightDp = 160,
)
@Composable
fun BingeWatcherSmallSeasonCardPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            BingeWatcherSmallSeasonCard(
                season = 1,
                episodesAmount = 7,
                imagePainter = painterResource(R.drawable.breaking_bad_s1),
                premiereDate = "2008",
                onClick = {}
            )
        }
    }
}