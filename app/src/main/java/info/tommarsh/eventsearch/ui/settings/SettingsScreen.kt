package info.tommarsh.eventsearch.ui.settings

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.theme.SettingsTheme

@Composable
fun SettingsScreen(backStackEntry: NavBackStackEntry) {

    val viewModel = viewModel<SettingsViewModel>(
        factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
    )

    val darkMode = viewModel.darkMode.collectAsState(initial = MODE_NIGHT_FOLLOW_SYSTEM)

    SettingsScreen(darkMode = darkMode.value)
}

@Composable
fun SettingsScreen(darkMode: Int) = SettingsTheme {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SettingsToolbar() }) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { SettingsTitle() }
        }
    }
}

@Composable
fun SettingsToolbar() {

    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
    )
}

@Composable
private fun SettingsTitle() {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(id = R.string.settings),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h4.copy(color = Color.White)
    )
}