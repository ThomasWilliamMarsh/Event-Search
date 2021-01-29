package info.tommarsh.eventsearch.ui.search.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.ui.common.EventSearchHorizontalCard

@Composable
internal fun LikedAttractionCard(
    likedModel: LikedAttractionModel,
    navigateToAttraction: (id: String) -> Unit
) {
    EventSearchHorizontalCard(onClick = { navigateToAttraction(likedModel.id) }) {

        Card(
            elevation = 8.dp,
            modifier = Modifier.width(128.dp).wrapContentHeight()
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