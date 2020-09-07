package info.tommarsh.eventsearch.ui.search

import android.widget.Toast
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.R
import info.tommarsh.eventsearch.ui.search.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.reflect.KProperty

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen() {
    val viewModel = viewModel<SearchViewModel>()
    val fetchState by viewModel.fetchState.collectAsState(initial = EventFetchState.Loading)

    SearchScreen(fetchState)
}

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(fetchState: EventFetchState) {
    Scaffold(
        topBar = { TopToolbar() },
        bodyContent = {
            when (fetchState) {
                is EventFetchState.Loading -> CenteredCircularProgress()
                is EventFetchState.Success -> SearchList(items = fetchState.events)
                is EventFetchState.Failure -> Toast.makeText(
                    ContextAmbient.current,
                    fetchState.throwable.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}

@Composable
private fun TopToolbar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            Icon(
                asset = vectorResource(id = R.drawable.ic_baseline_menu_24),
                tint = MaterialTheme.colors.onPrimary,
            )
        },
        elevation = 0.dp
    )
}


@Composable
fun SearchList(items: List<EventViewModel>) {
    LazyColumnForIndexed(items = items) { index, event ->
        if (index == 0) SearchField()
        EventCard(item = event)
    }
}

@Composable
fun CenteredCircularProgress() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { CircularProgressIndicator() }
}

@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventSearchApp {
        SearchScreen(
            EventFetchState.Success(
                events = listOf(
                    onSaleEvent,
                    comingSoonEvent,
                    presaleEvent
                )
            )
        )
    }
}

operator fun <T> State<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value