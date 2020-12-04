package info.tommarsh.eventsearch.ui.attractions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.AttractionDetailTheme
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.WithFetchState

@Composable
internal fun AttractionDetailScreen(viewModel: AttractionDetailViewModel) = AttractionDetailTheme {
    val attraction by viewModel.detailState.collectAsState()

    AttractionDetailScreen(attractionState = attraction)
}

@Composable
internal fun AttractionDetailScreen(attractionState: FetchState<AttractionDetailsViewModel>) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        bodyContent = {
            WithFetchState(
                state = attractionState,
                onFailure = {
                    ErrorSnackbar(
                        snackbarHostState = scaffoldState.snackbarHostState,
                        message = stringResource(id = R.string.error_loading_event_details)
                    )
                },
                onSuccess = { data ->
                    AttractionDetailContent(data)
                }
            )
        }
    )
}

@Composable
private fun AttractionDetailContent(attraction: AttractionDetailsViewModel) {
    LazyColumn {
        item {
            PosterImage(
                url = attraction.detailImage.orEmpty(),
                name = attraction.name,
                genre = attraction.genre,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        item {
            UnderlineTitle(
                text = stringResource(id = R.string.event_details_title),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 18.dp)
            )
        }

        item { CalendarList(attraction.events) }
    }
}

@Composable
private fun PosterImage(
    url: String,
    name: String,
    genre: String,
    modifier: Modifier = Modifier
) {
    val (loaded, setLoaded) = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CoilImage(
            data = url,
            onRequestCompleted = { setLoaded(true) },
            contentScale = ContentScale.FillWidth,
            fadeIn = true
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomStart)
        ) {
            if (loaded) {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(color = Color.White)
                )

            }
        }
    }
}

@Composable
private fun UnderlineTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = text.capitalize(), style = MaterialTheme.typography.h5)
        Box(
            Modifier.background(color = MaterialTheme.colors.onBackground)
                .height(2.dp)
                .width(24.dp)
        )
    }
}

@Composable
private fun CalendarList(events: List<EventViewModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight()
            .drawShadow(12.dp)
    ) {
        Column {
            events.forEach { event ->
                CalendarItem(event = event)
            }
        }
    }
}

@Composable
private fun CalendarItem(event: EventViewModel) {

    Providers(AmbientTextStyle provides MaterialTheme.typography.body1) {
        Row {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                    Text(text = event.month, textAlign = TextAlign.Center)
                }
                Providers(AmbientContentAlpha provides ContentAlpha.high) {
                    Text(text = event.day, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                    Text(text = event.dowAndTime)
                }
                Providers(AmbientContentAlpha provides ContentAlpha.high) {
                    Text(text = event.venue)
                }
            }
        }
    }
}

@Preview
@Composable
fun TestImage() {
    AttractionDetailTheme {
        PosterImage(
            url = "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg",
            name = "Example band",
            genre = "Rock / Pop"
        )
    }
}