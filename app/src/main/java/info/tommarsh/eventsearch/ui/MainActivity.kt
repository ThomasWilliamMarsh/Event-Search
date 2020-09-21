package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.TopLevel
import info.tommarsh.eventsearch.ui.search.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventSearchApp {
                TopLevel()
            }
        }
    }
}