package info.tommarsh.eventsearch.ui.search

import androidx.annotation.DrawableRes
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.koduok.compose.glideimage.GlideImage
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
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
fun EventCard(item: EventViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { }),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            PosterImage(url = item.imageUrl)

            ProvideEmphasis(EmphasisAmbient.current.high) {
                Text(
                    text = item.name,
                    style = typography.h4,
                )
            }

            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(
                    text = item.venue,
                    style = typography.subtitle1,
                )
            }

            Row(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 8.dp
                )
            ) {

                when (item.saleStatus) {
                    SaleStatus.COMING_SOON -> InfoPill(
                        resource = R.drawable.ic_ticket_24,
                        color = red500,
                        text = stringResource(id = R.string.coming_soon)
                    )
                    SaleStatus.PRESALE -> InfoPill(
                        resource = R.drawable.ic_ticket_24,
                        color = amber500,
                        text = stringResource(id = R.string.pre_sale)
                    )
                    SaleStatus.SALE -> InfoPill(
                        resource = R.drawable.ic_ticket_24,
                        color = green500,
                        text = stringResource(id = R.string.on_sale)
                    )
                    else -> {
                    }
                }
            }
        }
    }
}

@Composable
private fun WatchedOverlay() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
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
            GlideImage(url)
        }
    }
}

@Composable
private fun InfoPill(
    @DrawableRes resource: Int,
    color: Color,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier then Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        color = color,
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(asset = vectorResource(id = resource), tint = Color.White)
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun sampleEventItem() {

    EventSearchApp {
        EventCard(
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