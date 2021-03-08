package info.tommarsh.eventsearch.ui.attractions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.*
import info.tommarsh.eventsearch.navigation.Arguments
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.theme.AttractionDetailTheme
import info.tommarsh.eventsearch.ui.common.CenteredCircularProgress
import info.tommarsh.eventsearch.ui.common.ErrorSnackbar
import java.util.*

@Composable
internal fun AttractionDetailScreen(backStackEntry: NavBackStackEntry) {
    val id = backStackEntry.stringArg(Arguments.ID)
    val viewModel = viewModel<AttractionDetailViewModel>(
        factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
    )
    val attractionState by viewModel.attraction(id).collectAsState()
    val isLiked by viewModel.liked(id).collectAsState()

    AttractionDetailScreen(
        attractionState = attractionState,
        isLiked = isLiked,
        toggleLike = { attraction ->
            if (isLiked) {
                viewModel.removeLikedAttraction(attraction)
            } else {
                viewModel.addLikedAttraction(attraction)
            }
        }
    )
}

@Composable
internal fun AttractionDetailScreen(
    attractionState: FetchState<AttractionDetailsViewModel>,
    isLiked: Boolean,
    toggleLike: (attraction: LikedAttractionModel) -> Unit
) = AttractionDetailTheme {
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxHeight()
    ) {
        when (attractionState) {
            is FetchState.Loading -> CenteredCircularProgress()
            is FetchState.Success -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxHeight()
            ) {
                item {
                    PosterImage(
                        attraction = attractionState.data,
                        isLiked = isLiked,
                        toggleLike = toggleLike
                    )
                }

                item { UnderlineTitle(text = stringResource(id = R.string.event_details_title)) }

                item { CalendarList(attractionState.data.events) }
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
    attraction: AttractionDetailsViewModel,
    isLiked: Boolean,
    toggleLike: (attraction: LikedAttractionModel) -> Unit
) {
    val description = stringResource(if (isLiked) R.string.favourite else R.string.unFavourite)
    val icon = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder

    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        CoilImage(
            modifier = Modifier.sizeIn(minHeight = 128.dp),
            data = attraction.detailImage.orEmpty(),
            contentDescription = attraction.name,
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
            onCheckedChange = { toggleLike(attraction.toLikedAttraction()) },
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
        Column(modifier = Modifier.testTag("Calendar List")) {
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