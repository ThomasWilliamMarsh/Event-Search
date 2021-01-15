package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.model.FetchState

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
                color = AmbientContentColor.current,
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
    onClick: () -> Unit,
    content: @Composable () -> Unit

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}


@Composable
fun CenteredCircularProgress() {
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
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message = message)
    }
}

@Composable
fun <T> WithFetchState(
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
