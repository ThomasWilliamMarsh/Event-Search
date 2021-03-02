package info.tommarsh.eventsearch.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.theme.amber200
import info.tommarsh.eventsearch.theme.red200
import info.tommarsh.eventsearch.ui.common.EventSearchHorizontalCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun LikedAttractionCard(
    likedModel: LikedAttractionModel,
    deleteLikedAttraction: (LikedAttractionModel) -> Unit,
    navigateToAttraction: (id: String) -> Unit
) {
    val state = rememberDismissState { value ->
        when (value) {
            DismissValue.DismissedToEnd -> {
                deleteLikedAttraction(likedModel)
                true
            }
            else -> false
        }
    }

    SwipeToDismiss(
        state = state,
        dismissThresholds = { FractionalThreshold(0.75f) },
        background = {
            val direction = state.dismissDirection ?: return@SwipeToDismiss
            val color = when (direction) {
                StartToEnd -> red200
                EndToStart -> amber200
            }
            val icon = when (direction) {
                StartToEnd -> Icons.Outlined.Delete
                EndToStart -> Icons.Outlined.AddAlert
            }
            val alignment = when (direction) {
                StartToEnd -> Alignment.CenterStart
                EndToStart -> Alignment.CenterEnd
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp),
                contentAlignment = alignment
            ) {
                Icon(icon, "")
            }
        }) {
        DismissContent(likedModel = likedModel, navigateToAttraction = navigateToAttraction)
    }
}

@Composable
private fun DismissContent(
    likedModel: LikedAttractionModel,
    navigateToAttraction: (id: String) -> Unit
) {
    EventSearchHorizontalCard(
        onClick = { navigateToAttraction(likedModel.id) }) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .width(128.dp)
                .wrapContentHeight()
        ) {
            CoilImage(
                likedModel.imageUrl,
                contentDescription = likedModel.name,
                fadeIn = true
            )
        }

        Text(text = likedModel.name, modifier = Modifier.padding(start = 8.dp))
    }
}