package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel

internal data class SearchAttractionViewModel(
    val name: String,
    val id: String,
    val type: String,
    val url: String,
    val description: String?,
    val searchImage: String?,
    val detailImage: String?,
    val locale: String,
    val genre: String?,
    val numberOfEvents: String
)


internal fun AttractionModel.toViewModel(): SearchAttractionViewModel {
    return SearchAttractionViewModel(
        name = name,
        id = id,
        type = type,
        url = url,
        description = description,
        searchImage = searchImage,
        detailImage = detailImage,
        locale = locale,
        genre = genreName,
        numberOfEvents = numberOfEvents.toString()
    )
}