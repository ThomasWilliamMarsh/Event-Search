package info.tommarsh.eventsearch.attraction.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.attraction.R
import info.tommarsh.eventsearch.core.theme.gray600
import info.tommarsh.eventsearch.core.theme.red200

private enum class LikedState { Liked, Unliked }

private const val LikedLabel = "liked"

@Composable
fun LikedButton(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    onLikedClicked: () -> Unit
) {
    val likedState = if (isLiked) LikedState.Liked else LikedState.Unliked
    val transition = updateTransition(likedState, label = LikedLabel)
    val description = stringResource(if (isLiked) R.string.favourite else R.string.unFavourite)
    val colour by transition.animateColor(label = LikedLabel) { state ->
        when (state) {
            LikedState.Liked -> red200
            LikedState.Unliked -> gray600
        }
    }
    val size by transition.animateDp(label = LikedLabel) { state ->
        when (state) {
            LikedState.Liked -> 36.dp
            LikedState.Unliked -> 24.dp
        }
    }


    IconToggleButton(
        checked = isLiked,
        onCheckedChange = { onLikedClicked() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = description,
            tint = colour,
            modifier = Modifier.size(size)
        )
    }
}