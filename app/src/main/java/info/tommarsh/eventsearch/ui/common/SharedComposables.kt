package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.theme.EventSearchTypography

@Composable
fun TopToolbar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = contentColor(),
                style = MaterialTheme.typography.h6
            )
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
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
    launchInComposition {
        snackbarHostState.showSnackbar(message = message)
    }
}
