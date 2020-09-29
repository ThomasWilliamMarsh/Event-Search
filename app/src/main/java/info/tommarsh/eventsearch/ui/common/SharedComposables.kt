package info.tommarsh.eventsearch.ui.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import info.tommarsh.eventsearch.theme.EventSearchTypography

@Composable
fun TopToolbar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = contentColor(),
                style = MaterialTheme.typography.h6
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    )
}
