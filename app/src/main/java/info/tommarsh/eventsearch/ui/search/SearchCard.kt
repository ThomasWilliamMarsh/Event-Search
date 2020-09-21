package info.tommarsh.eventsearch.ui.search

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.Navigator
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.navigation.Screen
import info.tommarsh.eventsearch.theme.*
import info.tommarsh.eventsearch.ui.search.model.EventViewModel
import info.tommarsh.eventsearch.ui.search.model.SaleStatus

private val CornerRadius = DpPropKey()

private enum class WatchedState { Watched, NotWatched }

private val WatchedDefinition = transitionDefinition<WatchedState> {
    state(WatchedState.NotWatched) {
        this[CornerRadius] = 0.dp
    }

    state(WatchedState.Watched) {
        this[CornerRadius] = 72.dp
    }
}

@Composable
fun SearchCard(item: EventViewModel) {
    val navigator = Navigator.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { navigator.navigate(Screen.EVENT) })
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            PosterImage(url = item.imageUrl)

            ProvideEmphasis(EmphasisAmbient.current.high) {
                Text(text = item.name, style = typography.h4)
            }

            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(text = item.venue, style = typography.subtitle1)
            }
        }
    }
}

@Composable
private fun WatchedOverlay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(
            backgroundColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Icon(
                asset = vectorResource(id = R.drawable.ic_binoculars_24),
                tint = Color.White,
                modifier = Modifier
                    .gravity(Alignment.End)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun PosterImage(url: String) {
    val isWatched = remember { mutableStateOf(false) }
    val watchedTransition = transition(
        definition = WatchedDefinition,
        toState = if (isWatched.value) WatchedState.Watched else WatchedState.NotWatched
    )

    Stack {
        WatchedOverlay()
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .clickable(onClick = { isWatched.value = !isWatched.value })
                .aspectRatio(16 / 9F),
            shape = CutCornerShape(topRight = watchedTransition[CornerRadius])
        ) {
            CoilImage(url)
        }
    }
}

@Preview
@Composable
private fun sampleEventItem() {

    EventSearchApp {
        SearchCard(
            item = EventViewModel(
                id = "123",
                name = "The Book of Mormon",
                venue = "The Forum",
                dates = "13 July 2020",
                saleStatus = SaleStatus.SALE,
                imageUrl = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png"
            )
        )
    }
}