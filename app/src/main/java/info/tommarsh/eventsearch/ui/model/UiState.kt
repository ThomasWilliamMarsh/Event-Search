package info.tommarsh.eventsearch.ui.model

import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.CategoryViewModel

internal data class SearchScreenState(
    val likedAttractions: List<LikedAttractionModel> = emptyList(),
    val categories: List<CategoryViewModel> = emptyList(),
    val query: String = ""
)

internal sealed class SearchScreenAction {
    object SettingsButtonClicked : SearchScreenAction()
    data class SetReminder(val attraction: LikedAttractionModel) : SearchScreenAction()
    data class CategoryClicked(val id: String, val name: String) : SearchScreenAction()
    data class AttractionClicked(val id: String) : SearchScreenAction()
    data class QueryEntered(val query: String) : SearchScreenAction()
    data class AttractionDeleted(val model: LikedAttractionModel) : SearchScreenAction()
}

internal sealed class SearchScreenEffect {
    data class ShowReminderDialog(val id: String, val name: String, val image: String) :
        SearchScreenEffect()
}