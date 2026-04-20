package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme

sealed class BaseIcon {
    class ImageIcon(
        val iconVector: ImageVector
    ) : BaseIcon()

    class TextIcon(
        val iconText: String
    ) : BaseIcon()
}

@Composable
private fun BaseIcon(
    modifier: Modifier = Modifier,
    icon: BaseIcon,
    text: String? = null,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick?.let{ it() } }
        ) {
            when(icon) {
                is BaseIcon.ImageIcon ->
                    Icon(
                        imageVector = icon.iconVector,
                        contentDescription = contentDescription,
                    )
                is BaseIcon.TextIcon ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = icon.iconText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
            }
        }
        text?.let {
            Text(
                textAlign = TextAlign.Center,
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = LocalContentColor.current.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun BingeWatcherImdbIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.TextIcon(stringResource(R.string.imdb)),
        text = stringResource(R.string.imdb),
        contentDescription = stringResource(R.string.description_imdb),
        onClick = onClick
    )
}

@Composable
fun BingeWatcherLikeIcon(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(
            if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
        ),
        text = stringResource(R.string.like),
        contentDescription = stringResource(R.string.description_like),
        onClick = onClick
    )
}

@Composable
fun BingeWatcherOfficialSiteIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.Rounded.Link),
        text = stringResource(R.string.official_site),
        contentDescription = stringResource(R.string.description_official_site),
        onClick = onClick
    )
}

@Composable
fun BingeWatcherShareIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.Rounded.Share),
        text = stringResource(R.string.share),
        contentDescription = stringResource(R.string.description_share),
        onClick = onClick
    )
}

@Composable
fun BingeWatcherBackIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.AutoMirrored.Rounded.ArrowBack),
        contentDescription = stringResource(R.string.description_back),
        onClick = onClick,
    )
}

@Composable
internal fun BingeWatcherDiscoverIcon(
    modifier: Modifier = Modifier,
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.Rounded.Explore),
        contentDescription = stringResource(R.string.discover),
    )
}

@Composable
internal fun BingeWatcherSearchIcon(
    modifier: Modifier = Modifier,
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.Rounded.Search),
        contentDescription = stringResource(R.string.search),
    )
}

@Composable
internal fun BingeWatcherMyLibraryIcon(
    modifier: Modifier = Modifier,
) {
    BaseIcon(
        modifier = modifier,
        icon = BaseIcon.ImageIcon(Icons.Rounded.LocalLibrary),
        contentDescription = stringResource(R.string.my_library),
    )
}

@Preview(
    widthDp = 412,
    heightDp = 150,
)
@Composable
fun BingeWatcherImdbIconPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BingeWatcherImdbIcon(onClick = {})

                BingeWatcherLikeIcon(onClick = {}, isLiked = false)

                BingeWatcherOfficialSiteIcon(onClick = {})

                BingeWatcherShareIcon(onClick = {})
            }
        }
    }
}