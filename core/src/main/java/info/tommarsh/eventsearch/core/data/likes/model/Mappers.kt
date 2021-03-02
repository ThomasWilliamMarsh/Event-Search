package info.tommarsh.eventsearch.core.data.likes.model

import info.tommarsh.eventsearch.core.data.likes.model.data.LikedAttraction
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel

fun LikedAttraction.toDomainModel(): LikedAttractionModel {
    return LikedAttractionModel(id, name, imageUrl)
}

fun LikedAttractionModel.toDaoModel(): LikedAttraction {
    return LikedAttraction(id, name, imageUrl)
}