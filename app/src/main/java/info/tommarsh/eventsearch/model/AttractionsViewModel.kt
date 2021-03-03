package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailsModel
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel

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

internal data class AttractionDetailsViewModel(
    val id: String,
    val name: String,
    val genre: String,
    val description: String?,
    val numberOfEvents: Int,
    val detailImage: String?,
    val events: List<EventViewModel>
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

internal fun AttractionDetailsViewModel.toLikedAttraction(): LikedAttractionModel {
    return LikedAttractionModel(
        id = id,
        name = name,
        imageUrl = detailImage.orEmpty()
    )
}

internal fun AttractionDetailsModel.toViewModel(): AttractionDetailsViewModel {
    return AttractionDetailsViewModel(
        id = attraction.id,
        name = attraction.name,
        description = attraction.description,
        genre = attraction.genre.orEmpty(),
        numberOfEvents = attraction.numberOfEvents,
        detailImage = attraction.searchImage,
        events = events.map { event -> event.toViewModel() }
    )
}