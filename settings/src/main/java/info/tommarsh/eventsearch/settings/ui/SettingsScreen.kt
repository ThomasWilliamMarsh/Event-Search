package info.tommarsh.eventsearch.settings.ui

import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsPadding
import info.tommarsh.eventsearch.core.theme.SettingsTheme
import info.tommarsh.eventsearch.settings.R
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenAction
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenAction.NavigateBack
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenAction.NightModeChanged
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenState

@Composable
fun SettingsScreen(controller: NavHostController) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    SettingsScreen(
        viewModel = viewModel,
        controller = controller
    )
}

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel,
    controller: NavController
) {
    val screenState by viewModel.screenState.collectAsState()
    SettingsScreen(screenState = screenState) { action ->
        when (action) {
            is NavigateBack -> controller.popBackStack()
            else -> viewModel.postAction(action)
        }
    }
}

@Composable
internal fun SettingsScreen(
    screenState: SettingsScreenState,
    actionDispatcher: (SettingsScreenAction) -> Unit
) = SettingsTheme {
    val darkModeString = when (screenState.darkMode) {
        MODE_NIGHT_FOLLOW_SYSTEM -> stringResource(R.string.dark_mode_follow_system)
        MODE_NIGHT_NO -> stringResource(R.string.dark_mode_light)
        MODE_NIGHT_YES -> stringResource(R.string.dark_mode_dark)
        else -> throw IllegalArgumentException("Invalid Dark mode")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SettingsToolbar { actionDispatcher(NavigateBack) } }) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { SettingsTitle() }
            item {
                DarkModeList(
                    darkModeString,
                    onOptionSelected = { nightMode -> actionDispatcher(NightModeChanged(nightMode)) })
            }
        }
    }
}

@Composable
internal fun SettingsToolbar(navigateBack: () -> Unit) {

    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = stringResource(R.string.back),
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

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Column(modifier = Modifier.padding(16.dp)) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text(text = title, style = MaterialTheme.typography.h6)
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = subtitle, style = MaterialTheme.typography.subtitle1)
            }
        }
        content()
    }
}

@Composable
private fun DarkModeList(
    currentMode: String,
    onOptionSelected: (choice: Int) -> Unit
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    SettingItem(
        title = stringResource(R.string.dark_mode),
        subtitle = currentMode,
        onClick = { setExpanded(!expanded) }) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            offset = DpOffset(0.dp, 0.dp)
        ) {
            DropdownMenuItem(onClick = {
                onOptionSelected(MODE_NIGHT_YES)
                setExpanded(false)
            }) {
                Text(text = stringResource(id = R.string.dark_mode_dark))
            }

            DropdownMenuItem(onClick = {
                onOptionSelected(MODE_NIGHT_NO)
                setExpanded(false)
            }) {
                Text(text = stringResource(id = R.string.dark_mode_light))
            }

            DropdownMenuItem(onClick = {
                onOptionSelected(MODE_NIGHT_FOLLOW_SYSTEM)
                setExpanded(false)
            }) {
                Text(text = stringResource(id = R.string.dark_mode_follow_system))
            }
        }
    }
}