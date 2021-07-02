package info.tommarsh.eventsearch.attraction.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
    attraction: AttractionViewModel,
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
    attraction: AttractionViewModel,
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
            text = text.replaceFirstChar { it.uppercase() },
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

@Preview
@Composable
fun CalendarItemPreview() = AttractionDetailTheme {
    CalendarItem(
        event = EventViewModel(
            date = EventDateViewModel.Date(
                day = "21",
                month = "Sep",
                dowAndTime = "Mon - 11:00"
            ),
            venue = "test theatre"
        )
    )
}

@Composable
private fun CalendarItem(event: EventViewModel) {
    when (event.date) {
        is EventDateViewModel.Date -> {
            CalendarItem(date = event.date, venueName = event.venue)
        }
        is EventDateViewModel.NoDate -> {
            CalendarItem(reason = event.date.reason, venueName = event.venue)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CalendarItem(date: EventDateViewModel.Date, venueName: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        val (month, day, dowAndTime, venue) = createRefs()
        val dateBarrier = createEndBarrier(month, day)

        Text(
            modifier = Modifier.constrainAs(day) {
                linkTo(
                    top = parent.top,
                    bottom = month.top,
                    start = month.start,
                    end = month.end,
                )
            },
            text = date.day
        )

        Text(
            modifier = Modifier.constrainAs(month) {
                linkTo(
                    top = day.bottom,
                    start = parent.start,
                    end = dateBarrier,
                    bottom = parent.bottom,
                    horizontalBias = 0.0f
                )
            },
            text = date.month
        )
        Text(
            modifier = Modifier
                .constrainAs(dowAndTime) {
                    linkTo(
                        top = parent.top,
                        start = dateBarrier,
                        bottom = venue.top,
                        end = parent.end,
                        startMargin = 16.dp,
                        horizontalBias = 0.0f
                    )
                }
                .alpha(ContentAlpha.medium),
            text = date.dowAndTime
        )
        Text(
            modifier = Modifier.constrainAs(venue) {
                linkTo(
                    top = dowAndTime.bottom,
                    start = dateBarrier,
                    bottom = parent.bottom,
                    end = parent.end,
                    startMargin = 16.dp,
                    horizontalBias = 0.0f
                )
                width = Dimension.preferredWrapContent
            },
            text = venueName
        )
    }
}

@Preview
@Composable
fun NoDateCalenderItem() = AttractionDetailTheme {
    CalendarItem(reason = "TBC", venueName = "Test theatre")
}

@Composable
internal fun CalendarItem(reason: String, venueName: String) {
    Row(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = reason
        )

        Text(text = venueName)
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