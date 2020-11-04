package info.tommarsh.eventsearch.ui.search.screen

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.SaleStatus
import info.tommarsh.eventsearch.theme.EventHomeTheme

@Composable
fun SearchCard(
    event: EventViewModel,
    navigateToEvent: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { navigateToEvent(event.id) })
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            PosterImage(url = event.imageUrl)

            ProvideEmphasis(AmbientEmphasisLevels.current.high) {
                Text(text = event.name, style = MaterialTheme.typography.h4)
            }

            ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
                Text(text = event.venue, style = MaterialTheme.typography.subtitle1)
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
        CoilImage(url)
    }
}

@Preview
@Composable
private fun sampleEventItem() {

    EventHomeTheme {
        SearchCard(
            event = EventViewModel(
                id = "123",
                name = "The Book of Mormon",
                venue = "The Forum",
                dates = "13 July 2020",
                saleStatus = SaleStatus.SALE,
                promoterName = "Live Nation Music",
                imageUrl = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png"
            ),
            navigateToEvent = { _ -> Unit }
        )
    }
}