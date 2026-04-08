package com.bowazon.bingewatcher.core.designsystem.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.R

@JvmInline
internal value class Rating(val rating: String)

internal fun Rating.isNotEmpty() = rating.isNotEmpty()

// TODO can be private or internal, and we can pass all these things as parameters in BingeWatcherMediumEpisodeCard
class ToggleState(
    val isChecked: Boolean,
    val isLoading: Boolean = false,
)

@Composable
internal fun RatingWithStar(rating: Rating) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = rating.rating,
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


