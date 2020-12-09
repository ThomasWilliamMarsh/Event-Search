package info.tommarsh.eventsearch.core.data.attractions.model

import info.tommarsh.eventsearch.core.util.ScreenWidthResolver
import info.tommarsh.eventsearch.domain.AttractionModel

private const val RATIO_16_9 = "16_9"
private const val RATIO_3_2 = "3_2"

internal fun AttractionsResponse.toDomainModel(screenWidthResolver: ScreenWidthResolver): List<AttractionModel> {
    return _embedded?.attractions?.map { response ->
        response.toDomainModel(screenWidthResolver)
    }.orEmpty()
}

internal fun AttractionResponse.toDomainModel(screenWidthResolver: ScreenWidthResolver): AttractionModel {
    return AttractionModel(
        name = name,
        type = type,
        id = id,
        url = url.orEmpty(),
        locale = locale,
        genre = classifications.firstOrNull()?.genre?.name,
        searchImage = images.getLargestImage(screenWidthResolver, RATIO_16_9),
        detailImage = images.getLargestImage(screenWidthResolver, RATIO_3_2),
        numberOfEvents = upcomingEvents._total
    )
}

private fun List<ImageResponse>.getLargestImage(
    screenWidthResolver: ScreenWidthResolver,
    ratio: String
): String {
    val image = firstOrNull { it.ratio == ratio && it.width >= screenWidthResolver.get() }
        ?: filter { it.ratio == ratio }.maxBy { it.width }
    return image?.url.orEmpty()
}