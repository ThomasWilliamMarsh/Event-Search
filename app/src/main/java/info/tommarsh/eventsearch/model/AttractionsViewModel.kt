package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.domain.AttractionDetailsModel
import info.tommarsh.eventsearch.domain.AttractionModel

internal data class AttractionViewModel(
    val name: String,
    val id: String,
    val type: String,
    val url: String,
    val searchImage: String?,
    val detailImage: String?,
    val locale: String,
    val genre: String?,
    val numberOfEvents: Int
)

internal data class AttractionDetailsViewModel(
    val name: String,
    val genre: String,
    val numberOfEvents: Int,
    val detailImage: String?,
    val events: List<EventViewModel>
)

internal fun List<AttractionModel>.toViewModel(): List<AttractionViewModel> {
    return map { domain -> domain.toViewModel() }
}

internal fun AttractionModel.toViewModel(): AttractionViewModel {
    return AttractionViewModel(
        name = name,
        id = id,
        type = type,
        url = url,
        searchImage = searchImage,
        detailImage = detailImage,
        locale = locale,
        genre = genre,
        numberOfEvents = numberOfEvents
    )
}

internal fun AttractionDetailsModel.toViewModel(): AttractionDetailsViewModel {
    return AttractionDetailsViewModel(
        name = attraction.name,
        genre = attraction.genre.orEmpty(),
        numberOfEvents = attraction.numberOfEvents,
        detailImage = attraction.detailImage,
        events = events.map { event -> event.toViewModel() }
    )
}