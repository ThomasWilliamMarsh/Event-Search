package info.tommarsh.eventsearch.ui.search.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.core.ui.EventSearchVerticalCard

@Composable
internal fun SearchCard(
    attraction: AttractionViewModel,
    navigateToAttraction: (id: String) -> Unit
) {
    EventSearchVerticalCard(onClick = { navigateToAttraction(attraction.id) }) {

        PosterImage(
            url = attraction.searchImage.orEmpty(),
            contentDescription = attraction.name
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text(text = attraction.name, style = MaterialTheme.typography.h4)
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(
                    id = R.string.number_events,
                    formatArgs = arrayOf(attraction.numberOfEvents)
                ),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun PosterImage(url: String, contentDescription: String) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.aspectRatio(16 / 9F)
    ) {
        CoilImage(
            url,
            fadeIn = true,
            contentDescription = contentDescription
        )
    }
}