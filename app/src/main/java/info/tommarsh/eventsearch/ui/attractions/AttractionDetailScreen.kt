package info.tommarsh.eventsearch.ui.attractions

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.FetchState
import info.tommarsh.eventsearch.theme.EventDetailTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar

@Composable
internal fun AttractionDetailScreen(viewModel: AttractionDetailViewModel) = EventDetailTheme {
    val attraction by viewModel.detailState.collectAsState()

    AttractionDetailScreen(attractionState = attraction)
}

@Composable
internal fun AttractionDetailScreen(attractionState: FetchState<AttractionViewModel>) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        bodyContent = {
            when (attractionState) {
                is FetchState.Loading -> CenteredCircularProgress()
                is FetchState.Success -> AttractionDetailContent(attraction = attractionState.items)
                is FetchState.Failure -> ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_event_details)
                )
            }
        }
    )
}

@Composable
private fun AttractionDetailContent(attraction: AttractionViewModel) {
    LazyColumn {
        item {
            PosterImage(
                url = attraction.detailImage.orEmpty(),
                name = attraction.name,
                genre = attraction.genre.orEmpty(),
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
private fun CalendarList() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(600.dp)
            .drawShadow(12.dp)
    ) {

    }
}

@Preview
@Composable
fun TestImage() {
    EventDetailTheme {
        PosterImage(
            url = "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg",
            name = "Example band",
            genre = "Rock / Pop"
        )
    }
}