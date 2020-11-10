package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedTask
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    LaunchedTask {
        snackbarHostState.showSnackbar(message = message)
    }
}
