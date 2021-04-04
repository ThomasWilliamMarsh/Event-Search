package info.tommarsh.eventsearch.attraction.ui.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailsModel
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel

internal data class AttractionDetailsViewModel(
    val id: String,
    val name: String,
    val genre: String,
    val description: String?,
    val numberOfEvents: Int,
    val detailImage: String?,
    val events: List<EventViewModel>
)


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