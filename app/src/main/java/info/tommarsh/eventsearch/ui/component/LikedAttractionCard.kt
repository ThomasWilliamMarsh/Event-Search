package info.tommarsh.eventsearch.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.core.theme.red200
import info.tommarsh.eventsearch.core.ui.EventSearchHorizontalCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun LikedAttractionCard(
    likedModel: LikedAttractionModel,
    setAttractionReminder: (LikedAttractionModel) -> Unit,
    deleteLikedAttraction: (LikedAttractionModel) -> Unit,
    navigateToAttraction: (id: String) -> Unit
) {
    val state = rememberDismissState { value ->
        when (value) {
            DismissValue.DismissedToEnd -> {
                deleteLikedAttraction(likedModel)
                return@rememberDismissState true
            }
            DismissValue.DismissedToStart -> {
                setAttractionReminder(likedModel)
                return@rememberDismissState false
            }
            else -> false
        }
    }

    SwipeToDismiss(
        state = state,
        directions = setOf(StartToEnd),
        dismissThresholds = { FractionalThreshold(0.75f) },
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(red200)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(Icons.Outlined.Delete, "Delete")
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
            Image(
                painter = rememberCoilPainter(request = likedModel.imageUrl, fadeIn = true),
                contentDescription = likedModel.name,
            )
        }

        Text(text = likedModel.name, modifier = Modifier.padding(start = 8.dp))
    }
}