package info.tommarsh.eventsearch.ui.category.model

internal sealed class CategoryScreenAction {
    data class ClickedAttractions(val id: String) : CategoryScreenAction()
}