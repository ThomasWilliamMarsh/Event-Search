package info.tommarsh.eventsearch.ui.search.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import attraction
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.theme.EventHomeTheme

@Composable
internal fun SearchCard(
    attraction: AttractionViewModel,
    navigateToAttraction: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { navigateToAttraction(attraction.id) })
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            PosterImage(url = attraction.searchImage.orEmpty())

            Providers(AmbientContentAlpha provides ContentAlpha.high) {
                Text(text = attraction.name, style = MaterialTheme.typography.h4)
            }

            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
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
}

@Composable
private fun PosterImage(url: String) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.aspectRatio(16 / 9F)
    ) {
        CoilImage(url, fadeIn = true)
    }
}

@Preview
@Composable
private fun sampleAttractionItem() {

    EventHomeTheme {
        SearchCard(
            attraction = attraction,
            navigateToAttraction = {}
        )
    }
}