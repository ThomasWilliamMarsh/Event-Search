package info.tommarsh.eventsearch.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.insets.navigationBarsPadding
import info.tommarsh.eventsearch.core.R

@Composable
fun AttractionCard(
    name: String,
    numberOfEvents: String,
    imageUrl: String? = null,
    onClick: () -> Unit
) {
    EventSearchVerticalCard(onClick = { onClick() }) {
        PosterImage(
            url = imageUrl.orEmpty(),
            contentDescription = name
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Text(text = name, style = MaterialTheme.typography.h4)
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "$numberOfEvents ${stringResource(id = R.string.events)}",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun PosterImage(url: String, contentDescription: String) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.aspectRatio(16 / 9F)
    ) {
        CoilImage(
            url,
            fadeIn = true,
            contentDescription = contentDescription
        )
    }
}


@Composable
fun TopToolbar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = LocalContentColor.current,
                style = MaterialTheme.typography.h6
            )
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        navigationIcon = navigationIcon,
        modifier = modifier
    )
}

@Composable
fun BorderButton(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.onPrimary,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight() then modifier
    ) {
        content()
    }
}

@Composable
fun EventSearchVerticalCard(
    onClick: () -> Unit,
    content: @Composable () -> Unit

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun EventSearchHorizontalCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = listState.firstVisibleItemIndex > 0,
        modifier = modifier.then(Modifier.padding(16.dp))
    ) {
        FloatingActionButton(
            onClick = onClick,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowUpward,
                contentDescription = stringResource(R.string.scroll_to_top)
            )
        }
    }
}

@Composable
fun CenteredCircularProgress() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message = message)
    }
}

//Region Paging 3 composable
@Composable
fun RetryView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.error)
            .padding(top = 16.dp)
            .navigationBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = stringResource(id = R.string.error_loading_page),
            style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onError),
        )

        BorderButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            borderColor = MaterialTheme.colors.onError,
            onClick = onRetry
        ) {
            Text(text = stringResource(id = R.string.retry), color = MaterialTheme.colors.onError)
        }
    }
}

@Composable
fun <T : Any> LoadStateFooter(
    items: LazyPagingItems<T>,
    onLoading: @Composable () -> Unit = { CenteredCircularProgress() },
    onError: @Composable () -> Unit = { RetryView(onRetry = { items.retry() }) },
    onLoaded: @Composable () -> Unit = {}
) {
    when (items.loadState.append) {
        is LoadState.Loading -> onLoading()
        is LoadState.Error -> onError()
        is LoadState.NotLoading -> onLoaded()
    }
}
//EndRegion