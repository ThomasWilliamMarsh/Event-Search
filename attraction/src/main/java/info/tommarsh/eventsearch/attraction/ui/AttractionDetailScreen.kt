package info.tommarsh.eventsearch.attraction.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.attraction.R
import info.tommarsh.eventsearch.attraction.ui.model.*
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.ClickLiked
import info.tommarsh.eventsearch.core.data.FetchState
import info.tommarsh.eventsearch.core.theme.AttractionDetailTheme
import info.tommarsh.eventsearch.core.ui.CenteredCircularProgress
import info.tommarsh.eventsearch.core.ui.ErrorSnackbar
import java.util.*

@Composable
fun AttractionDetailScreen() {
    val viewModel = hiltViewModel<AttractionDetailViewModel>()
    AttractionDetailScreen(viewModel)
}

@Composable
internal fun AttractionDetailScreen(
    viewModel: AttractionDetailViewModel
) {
    val screenState by viewModel.screenState.collectAsState()

    AttractionDetailScreen(screenState = screenState) { action ->
        viewModel.postAction(action)
    }
}

@Composable
internal fun AttractionDetailScreen(
    screenState: AttractionDetailScreenState,
    actionDispatcher: (AttractionDetailScreenAction) -> Unit
) = AttractionDetailTheme {
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxHeight()
    ) {
        when (val fetchState = screenState.fetchState) {
            is FetchState.Loading -> CenteredCircularProgress()
            is FetchState.Success -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxHeight()
            ) {
                item {
                    PosterImage(
                        attraction = fetchState.data,
                        isLiked = screenState.isLiked,
                        onLikedClicked = { actionDispatcher(ClickLiked(fetchState.data.toLikedAttraction())) }
                    )
                }

                item {
                    CollapsableSection(sectionName = stringResource(id = R.string.event_details_title)) {
                        CalendarList(fetchState.data.events)
                    }
                }

                if (fetchState.data.relatedAttractions.isNotEmpty()) {
                    item {
                        CollapsableSection(
                            sectionName = stringResource(
                                id = R.string.similar_to_section,
                                fetchState.data.name
                            )
                        ) {
                            RelatedAttractions(fetchState.data.relatedAttractions)
                        }
                    }
                }
            }
            is FetchState.Failure -> ErrorSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                message = stringResource(id = R.string.error_loading_event_details)
            )
        }
    }
}

@Composable
private fun PosterImage(
    modifier: Modifier = Modifier,
    attraction: info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailViewModel,
    isLiked: Boolean,
    onLikedClicked: () -> Unit
) {
    val description = stringResource(if (isLiked) R.string.favourite else R.string.unFavourite)
    val icon = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder

    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberCoilPainter(
                request = attraction.detailImage.orEmpty(),
                fadeIn = true
            ),
            modifier = Modifier.sizeIn(minHeight = 128.dp),
            contentDescription = attraction.name,
            contentScale = ContentScale.FillWidth,
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
            Text(
                text = attraction.genre,
                style = MaterialTheme.typography.subtitle2.copy(color = Color.White)
            )
            Text(
                text = attraction.name,
                style = MaterialTheme.typography.h4.copy(color = Color.White)
            )
        }

        IconToggleButton(
            checked = isLiked,
            onCheckedChange = { onLikedClicked() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
                .testTag("LikedIcon")
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = Color.White,
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsableSection(sectionName: String, content: @Composable () -> Unit) {
    val (expanded, setExpanded) = remember { mutableStateOf(true) }
    Column {
        UnderlineTitle(text = sectionName, isExpanded = expanded) { setExpanded(!expanded) }
        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}

@Composable
private fun UnderlineTitle(
    text: String,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
) {
    val icon = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    val description = stringResource(id = R.string.toggle_section)
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Column {
            Text(text = text.capitalize(Locale.ENGLISH), style = MaterialTheme.typography.h5)
            Box(
                Modifier
                    .background(color = MaterialTheme.colors.onBackground)
                    .height(2.dp)
                    .width(24.dp)
            )
        }

        IconToggleButton(
            checked = isExpanded,
            onCheckedChange = { onToggleExpanded() },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun CalendarList(events: List<EventViewModel>) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag("Calendar List")
    ) {
        events.forEach { event ->
            CalendarItem(event = event)
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
        Row(modifier = Modifier.testTag("Row With Date")) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .defaultMinSize(minWidth = 64.dp)
            ) {
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
        Row(
            Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .testTag("Row With No Date")
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = reason, modifier = Modifier
                        .padding(end = 16.dp)
                        .defaultMinSize(minWidth = 64.dp)
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(text = venue)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RelatedAttractions(attractions: List<RelatedAttractionViewModel>) {
    LazyRow {
        items(attractions) { attraction ->
            Image(
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp),
                painter = rememberCoilPainter(request = attraction.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}