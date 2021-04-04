package info.tommarsh.eventsearch.category.ui.model

internal sealed class CategoryScreenAction {
    data class ClickedAttractions(val id: String) : CategoryScreenAction()
}