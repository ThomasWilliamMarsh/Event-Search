package info.tommarsh.eventsearch.ui.attractions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import attractionDetail
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.*
import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.EventDateViewModel
import info.tommarsh.eventsearch.model.EventViewModel
import info.tommarsh.eventsearch.model.toLikedAttraction
import info.tommarsh.eventsearch.theme.AttractionDetailTheme
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import info.tommarsh.eventsearch.ui.common.WithFetchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

@Composable
internal fun AttractionDetailScreen(
    fetchFlow: Flow<FetchState<AttractionDetailsViewModel>>,
    likedFlow: Flow<Boolean>,
    onLiked: (attraction: LikedAttractionModel) -> Unit,
    onUnliked: (attraction: LikedAttractionModel) -> Unit
) = AttractionDetailTheme {
    val attractionState by fetchFlow.collectAsState(initial = FetchState.Loading(true))
    val isLiked by likedFlow.collectAsState(initial = false)

    AttractionDetailScreen(
        attractionState = attractionState,
        isLiked = isLiked,
        toggleLike = { attraction ->
            if (isLiked) {
                onUnliked(attraction)
            } else {
                onLiked(attraction)
            }
        }
    )
}

@Composable
private fun AttractionDetailScreen(
    attractionState: FetchState<AttractionDetailsViewModel>,
    isLiked: Boolean,
    toggleLike: (attraction: LikedAttractionModel) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        WithFetchState(
            state = attractionState,
            onFailure = {
                ErrorSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    message = stringResource(id = R.string.error_loading_event_details)
                )
            },
            onSuccess = { data ->
                AttractionDetailContent(data, isLiked, toggleLike)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AttractionDetailContent(
    attraction: AttractionDetailsViewModel,
    isLiked: Boolean,
    toggleLike: (id: LikedAttractionModel) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        item {
            PosterImage(
                attraction = attraction,
                isLiked = isLiked,
                toggleLike = toggleLike
            )
        }

        stickyHeader { MenuStrip(listState) }

        item { UnderlineTitle(text = stringResource(id = R.string.event_details_title)) }

        item { CalendarList(attraction.events) }

        item { UnderlineTitle(text = stringResource(id = R.string.about_attraction_title)) }

        item { AboutExcerpt(attraction.description ?: "No description") }
    }
}

@Composable
private fun PosterImage(
    modifier: Modifier = Modifier,
    attraction: AttractionDetailsViewModel,
    isLiked: Boolean,
    toggleLike: (attraction: LikedAttractionModel) -> Unit
) {
    val (loaded, setLoaded) = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CoilImage(
            data = attraction.detailImage.orEmpty(),
            contentDescription = attraction.name,
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
                    text = attraction.genre,
                    style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
                )
                Text(
                    text = attraction.name,
                    style = MaterialTheme.typography.h4.copy(color = Color.White)
                )

            }
        }
        Icon(
            imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(R.string.favourite),
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
                .clickable(onClick = { toggleLike(attraction.toLikedAttraction()) })
        )
    }
}

@Composable
private fun MenuStrip(listState: LazyListState) {
    val (selectedIndex, setSelectedIndex) = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.onBackground,
        contentColor = MaterialTheme.colors.background
    ) {
        Tab(selected = selectedIndex == 0,
            onClick = {
                setSelectedIndex(0)
                scope.launch { listState.animateScrollToItem(1) }
            }) {
            Text(
                text = stringResource(id = R.string.event_details_title), modifier = Modifier
                    .padding(8.dp)
                    .statusBarsPadding()
            )
        }
        Tab(selected = selectedIndex == 1, onClick = {
            setSelectedIndex(1)
            scope.launch { listState.animateScrollToItem(4) }
        }) {
            Text(
                text = stringResource(id = R.string.about_attraction_title), modifier = Modifier
                    .padding(8.dp)
                    .statusBarsPadding()
            )
        }
    }
}

@Composable
private fun UnderlineTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.then(Modifier.padding(16.dp))) {
        Text(text = text.capitalize(Locale.ENGLISH), style = MaterialTheme.typography.h5)
        Box(
            Modifier
                .background(color = MaterialTheme.colors.onBackground)
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
            .padding(start = 16.dp, end = 16.dp)
            .wrapContentHeight()
            .shadow(12.dp)
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
    when (event.date) {
        is EventDateViewModel.Date -> {
            RowWithDate(date = event.date, venue = event.venue)
        }
        EventDateViewModel.TBA -> {
            RowWithNoDate(reason = "TBA", venue = event.venue)
        }
        EventDateViewModel.TBC -> {
            RowWithNoDate(reason = "TBC", venue = event.venue)
        }
    }
}

@Composable
private fun RowWithDate(date: EventDateViewModel.Date, venue: String) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.body1) {
        Row {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = date.month, textAlign = TextAlign.Center)
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    Text(text = date.day, textAlign = TextAlign.Center)
                }
            }
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = date.dowAndTime)
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    Text(text = venue)
                }
            }
        }
    }
}

@Composable
private fun RowWithNoDate(reason: String, venue: String) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.body1) {
        Row {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = reason)
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(text = venue)
            }
        }
    }
}

@Composable
private fun AboutExcerpt(description: String) {
    Text(
        text = description,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
private fun TestImage() {
    AttractionDetailTheme {
        PosterImage(
            attraction = attractionDetail,
            isLiked = false,
            toggleLike = {}
        )
    }
}