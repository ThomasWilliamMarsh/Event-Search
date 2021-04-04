package info.tommarsh.eventsearch.category.ui.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel

internal data class CategoryAttractionViewModel(
    val name: String,
    val id: String,
    val searchImage: String?,
    val numberOfEvents: String
)


internal fun AttractionModel.toViewModel(): CategoryAttractionViewModel {
    return CategoryAttractionViewModel(
        name = name,
        id = id,
        searchImage = searchImage,
        numberOfEvents = numberOfEvents.toString()
    )
}