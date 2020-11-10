package info.tommarsh.eventsearch.ui.event

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventDetailTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.TopToolbar

@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel) = EventDetailTheme {
    val event by viewModel.detailState.collectAsState()

    EventDetailScreen(eventState = event)
}

@Composable
fun EventDetailScreen(eventState: FetchState<EventViewModel>) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        bodyContent = {
            when (eventState) {
                is FetchState.Loading -> CenteredCircularProgress()
                is FetchState.Success -> EventDetailContent(event = eventState.items)
                is FetchState.Failure -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_event_details)
                )
            }
        }
    )
}

@Composable
private fun EventDetailContent(event: EventViewModel) {
    LazyColumn {
        item {
            PosterImage(
                url = event.imageUrl,
                name = event.name,
                promoter = event.promoterName,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        item {
            UnderlineTitle(
                text = stringResource(id = R.string.event_details_title),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 18.dp)
            )
        }

        item { CalendarList() }
    }
}

@Composable
private fun PosterImage(
    url: String,
    name: String,
    promoter: String,
    modifier: Modifier = Modifier
) {
    val (loaded, setLoaded) = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CoilImage(data = url, onRequestCompleted = { setLoaded(true) })
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
                    text = promoter,
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
private fun CalendarList() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(600.dp)
            .drawShadow(12.dp)) {

    }
}

@Preview
@Composable
fun TestImage() {
    EventDetailTheme {
        PosterImage(
            url = "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg",
            name = "Example band",
            promoter = "Live Nation Music"
        )
    }
}