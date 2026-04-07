package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme

val SMALL_PERSON_CARD_SIZE = 100.dp
val SMALL_SEASON_CARD_WIDTH = 120.dp
val SMALL_SEASON_CARD_HEIGHT = 150.dp

@Composable
fun BingeWatcherSmallPersonCard(
    modifier: Modifier = Modifier,
    personName: String,
    imagePainter: Painter,
    additionalInfo: String? = null,
    additionalInfoHeight: Dp = 0.dp
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


@Composable
fun BingeWatcherSmallSeasonCard(
    modifier: Modifier = Modifier,
    season: Int,
    episodesAmount: Int,
    imagePainter: Painter,
    premiereDate: String
) {
    Column(modifier) {
        Text(text = "Season $season")
        Box(
            modifier = Modifier
                .height(SMALL_SEASON_CARD_HEIGHT)
                .width(SMALL_SEASON_CARD_WIDTH)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (episodesAmount != 0) {
                Text(
                    text = "E$episodesAmount",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        //.padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                )
            }

            Text(
                text = premiereDate,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    //.padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            )
        }
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
                premiereDate = "2008"
            )
        }
    }
}