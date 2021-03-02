package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.model.FetchState

@Composable
internal fun TopToolbar(
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
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp,
        navigationIcon = navigationIcon,
        modifier = modifier
    )
}

@Composable
internal fun BorderButton(
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
internal fun EventSearchVerticalCard(
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
internal fun EventSearchHorizontalCard(
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


@Composable
internal fun CenteredCircularProgress() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { CircularProgressIndicator() }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message = message)
    }
}

@Composable
internal fun <T> WithFetchState(
    state: FetchState<T>,
    onLoading: @Composable () -> Unit = { CenteredCircularProgress() },
    onFailure: @Composable (throwable: Throwable) -> Unit,
    onSuccess: @Composable (data: T) -> Unit
) {
    when (state) {
        is FetchState.Loading -> onLoading()
        is FetchState.Success -> onSuccess(state.data)
        is FetchState.Failure -> onFailure(state.throwable)
    }
}

//Region Paging 3 composables
@Composable
private fun RetryView(onRetry: () -> Unit) {
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
internal fun <T : Any> WithPagingRefreshState(
    items: LazyPagingItems<T>,
    onLoading: @Composable () -> Unit = { CenteredCircularProgress() },
    onError: @Composable () -> Unit = { },
    onLoaded: @Composable () -> Unit = {}
) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        when (items.loadState.refresh) {
            is LoadState.Loading -> onLoading()
            is LoadState.Error -> onError()
            is LoadState.NotLoading -> onLoaded()
        }
    }
}

@Composable
internal fun <T : Any> WithPagingAppendState(
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


//Region previews
@Preview(showBackground = true)
@Composable
fun RetryViewPreview() {
    RetryView(onRetry = { })
}
//Endregion