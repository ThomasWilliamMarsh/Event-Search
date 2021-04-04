package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel

internal data class AttractionViewModel(
    val name: String,
    val id: String,
    val type: String,
    val url: String,
    val description: String?,
    val searchImage: String?,
    val detailImage: String?,
    val locale: String,
    val genre: String?,
    val numberOfEvents: Int
)


internal fun AttractionModel.toViewModel(): AttractionViewModel {
    return AttractionViewModel(
        name = name,
        id = id,
        type = type,
        url = url,
        description = description,
        searchImage = searchImage,
        detailImage = detailImage,
        locale = locale,
        genre = genre,
        numberOfEvents = numberOfEvents
    )
}