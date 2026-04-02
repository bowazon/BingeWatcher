package com.example.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.utils.HtmlText


// TODO all Color.Black should be borrowed from theme

@Composable
fun BingeWatcherShowRestDescription(
    modifier: Modifier = Modifier,
    genres: String,
    description: String,
) {
    Surface(color = Color.Black,
        modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = genres,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            HtmlText(
                html = description,
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Composable
fun BingeWatcherShowShortDescription(
    modifier: Modifier = Modifier,
    name: String,
    premiered: String,
    rating: String
) {
    Surface(color = Color.Black) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = premiered,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (rating.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
}

@Composable
fun BingeWatcherShowImageWithGradient(
    modifier: Modifier = Modifier,
    imageHeight: Dp,
    imagePainter: Painter
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        Surface(color = Color.Black) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height * 0.95f,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient)
                    }
                }
        )
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
    Column {
        Text(text = "Season $season", color = Color.White)
        Box(
            modifier = modifier
                .height(150.dp)
                .width(120.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (episodesAmount != 0) {
                Image(
                    painter = painterResource(R.drawable.episodes_icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .height(50.dp)
                )

                Text(
                    text = episodesAmount.toString(),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        //.padding(8.dp)
                        //.background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 2.dp, vertical = 1.dp),
                    color = Color.Black
                )
            }

            Text(
                text = premiereDate,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    //.padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun BingeWatcherActorSmallCard(
    modifier: Modifier = Modifier,
    actorName: String,
    imagePainter: Painter
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(100.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = actorName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = actorName,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun BingeWatcherActorSmallCardPreview() {
    BingeWatcherActorSmallCard(
        actorName = "Jone Jones",
        imagePainter = painterResource(R.drawable.ic_launcher_foreground)
    )
}

@Preview
@Composable
fun BingeWatcherSmallSeasonCardPreview() {
    BingeWatcherSmallSeasonCard(
        season = 1,
        episodesAmount = 11,
        imagePainter = painterResource(R.drawable.breaking_bad_official),
        premiereDate = "2012"
    )
}