package info.tommarsh.eventsearch.ui.attractions

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import attractionDetail
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import info.tommarsh.eventsearch.model.FetchState
import org.junit.Rule
import org.junit.Test

class AttractionDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun poster_image_displays_correctly_liked() {
        composeTestRule.run {
            setContent {
                ProvideWindowInsets {
                    AttractionDetailScreen(
                        attractionState = FetchState.Success(data = attractionDetail),
                        isLiked = true,
                        toggleLike = { })
                }
                onNodeWithTag("LikedIcon").assertIsOn()
                onNodeWithText("Theatre").assertIsDisplayed()
                onNodeWithText("The Book of Mormon").assertIsDisplayed()
                onNodeWithText("Events").assertIsDisplayed()
            }
        }
    }

    @Test
    fun poster_image_displays_correctly_unliked() {
        composeTestRule.run {
            setContent {
                ProvideWindowInsets {
                    AttractionDetailScreen(
                        attractionState = FetchState.Success(data = attractionDetail),
                        isLiked = false,
                        toggleLike = { })
                }
            }

            onNodeWithTag("LikedIcon").assertIsOff()
            onNodeWithText("Theatre").assertIsDisplayed()
            onNodeWithText("The Book of Mormon").assertIsDisplayed()
            onNodeWithText("Events").assertIsDisplayed()
        }
    }

    @Test
    fun events_section_shows_events() {
        composeTestRule.run {
            setContent {
                ProvideWindowInsets {
                    AttractionDetailScreen(
                        attractionState = FetchState.Success(data = attractionDetail),
                        isLiked = true,
                        toggleLike = { })
                }
            }

            onNodeWithTag("Calendar List")
                .assert(SemanticsMatcher("Displays all items") { node ->
                    node.children.size == attractionDetail.numberOfEvents
                })

            onNodeWithText("TBC").assertExists()
            onNodeWithText("TBA").assertExists()
        }
    }
}