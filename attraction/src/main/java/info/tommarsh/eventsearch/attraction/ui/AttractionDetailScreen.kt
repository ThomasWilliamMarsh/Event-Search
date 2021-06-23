package info.tommarsh.eventsearch.attraction.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.attraction.R
import info.tommarsh.eventsearch.attraction.ui.model.*
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.ClickLiked
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailScreenAction.ClickedRelated
import info.tommarsh.eventsearch.core.data.FetchState
import info.tommarsh.eventsearch.core.navigation.Screen
import info.tommarsh.eventsearch.core.theme.AttractionDetailTheme
import info.tommarsh.eventsearch.core.ui.CenteredCircularProgress
import info.tommarsh.eventsearch.core.ui.ErrorSnackbar
import java.util.*
import info.tommarsh.eventsearch.attraction.ui.model.AttractionDetailViewModel as DetailModel

@Composable
fun AttractionDetailScreen(
    controller: NavController
) {
    val viewModel = hiltViewModel<AttractionDetailViewModel>()
    AttractionDetailScreen(
        viewModel = viewModel,
        controller = controller
    )
}

@Composable
internal fun AttractionDetailScreen(
    viewModel: AttractionDetailViewModel,
    controller: NavController
) {
    val screenState by viewModel.screenState.collectAsState()

    AttractionDetailScreen(screenState = screenState) { action ->
        when (action) {
            is ClickedRelated -> controller.navigate(Screen.Attraction.route(action.id))
            else -> viewModel.postAction(action)
        }
    }
}

@Composable
internal fun AttractionDetailScreen(
    screenState: AttractionDetailScreenState,
    actionDispatcher: (AttractionDetailScreenAction) -> Unit
) = AttractionDetailTheme {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxHeight()
    ) {
        when (val fetchState = screenState.fetchState) {
            is FetchState.Loading -> CenteredCircularProgress()
            is FetchState.Failure -> ErrorSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                message = stringResource(id = R.string.error_loading_event_details)
            )
            is FetchState.Success -> AttractionDetailList(
                attraction = fetchState.data,
                isLiked = screenState.isLiked,
                onLikeClicked = { actionDispatcher(ClickLiked(fetchState.data.toLikedAttraction())) },
                onRelatedAttractionClicked = { id -> actionDispatcher(ClickedRelated(id)) }
            )
        }
    }
}

@Composable
private fun AttractionDetailList(
    attraction: DetailModel,
    isLiked: Boolean,
    onLikeClicked: () -> Unit,
    onRelatedAttractionClicked: (id: String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
    ) {
        PosterImage(
            modifier = Modifier.graphicsLayer {
                alpha = 1f.coerceAtMost(1 - (scrollState.value / 600f))
                translationY = -scrollState.value * 0.1f
            },
            attraction = attraction,
            isLiked = isLiked,
            onLikedClicked = { onLikeClicked() }
        )

        Section(sectionName = stringResource(id = R.string.event_details_title)) {
            attraction.events.forEach { event ->
                CalendarItem(event = event)
            }
        }

        if (attraction.relatedAttractions.isNotEmpty()) {
            Section(
                sectionName = stringResource(id = R.string.similar_to_section, attraction.name)
            ) {
                RelatedAttractions(
                    attractions = attraction.relatedAttractions,
                    onRelatedAttractionClicked = onRelatedAttractionClicked
                )
            }
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

        LikedButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
                .testTag("LikedIcon"),
            isLiked = isLiked,
            onLikedClicked = onLikedClicked
        )
    }
}

@Composable
private fun Section(
    sectionName: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        UnderlineTitle(text = sectionName)
        content()
    }
}

@Composable
private fun UnderlineTitle(text: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = text.capitalize(Locale.ENGLISH),
            style = MaterialTheme.typography.h5
        )
        Box(
            Modifier
                .background(color = MaterialTheme.colors.onBackground)
                .height(2.dp)
                .width(24.dp)
        )
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
private fun RelatedAttractions(
    attractions: List<RelatedAttractionViewModel>,
    onRelatedAttractionClicked: (id: String) -> Unit
) {
    LazyRow(modifier = Modifier.navigationBarsPadding()) {
        items(attractions) { attraction ->
            Image(
                modifier = Modifier
                    .height(128.dp)
                    .aspectRatio(1.5f)
                    .clickable { onRelatedAttractionClicked(attraction.id) },
                painter = rememberCoilPainter(request = attraction.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}