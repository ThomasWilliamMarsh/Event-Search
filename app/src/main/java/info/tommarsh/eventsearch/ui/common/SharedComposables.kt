package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.model.FetchState

@Composable
fun TopToolbar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier
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
        modifier = modifier
    )
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
