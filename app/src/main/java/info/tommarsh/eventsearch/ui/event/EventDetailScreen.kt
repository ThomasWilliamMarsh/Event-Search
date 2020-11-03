package info.tommarsh.eventsearch.ui.event

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.TopToolbar

@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel) {
    val event by viewModel.detailState.collectAsState()

    EventDetailScreen(eventState = event)
}

@Composable
fun EventDetailScreen(eventState: FetchState<EventViewModel>) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { TopToolbar(title = stringResource(id = R.string.event_details_title)) },
        scaffoldState = scaffoldState,
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
        item { PosterImage(url = event.imageUrl) }
        item { UnderlineTitle(text = stringResource(id = R.string.event_details_title)) }
    }
}

@Composable
private fun EventDetailsList(eventDetail: EventViewModel) {

}

@Composable
private fun PosterImage(url: String) {
    val (loaded, setLoaded) = remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        CoilImage(data = url, onRequestCompleted = { setLoaded(true) })
        Column(
            Modifier.padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomStart)
        ) {
            if (loaded) {
                Text(
                    text = "Rock/Pop",
                    style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
                )
                Text(
                    text = "Waterparks",
                    style = MaterialTheme.typography.h4.copy(color = Color.White)
                )

            }
        }
    }
}

@Composable
private fun UnderlineTitle(text: String) {

}

@Composable
private fun CalenderList() {

}

@Preview
@Composable
fun TestImage() {
    EventSearchApp {
        PosterImage(url = "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg")
    }
}