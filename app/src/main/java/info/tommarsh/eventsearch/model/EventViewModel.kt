package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.domain.EventModel
import info.tommarsh.eventsearch.domain.PresalesModel
import info.tommarsh.eventsearch.domain.PublicModel
import org.joda.time.DateTime
import java.util.*

data class EventViewModel(
    val id: String,
    val name: String,
    val venue: String,
    val dates: String,
    val saleStatus: SaleStatus,
    val imageUrl: String
)

enum class SaleStatus {
    PRESALE,
    SALE,
    COMING_SOON,
    UNKNOWN
}

//Mappers
fun List<EventModel>.toViewModel(): List<EventViewModel> {
    val todaysDate = DateTime(Date().time)

    return map { domain -> domain.toViewModel(todaysDate)}
}

fun EventModel.toViewModel(todaysDate: DateTime) : EventViewModel {
    return EventViewModel(
        id = id,
        name = name,
        venue = venue,
        dates = initialStartDateTime.toDateString(),
        imageUrl = imageUrl,
        saleStatus = salesStatus(todaysDate)
    )
}

private fun String?.toDateString(): String {
    return if (this == null) "" else {
        val dateTime = DateTime(this)
        "${dateTime.dayOfMonth().get()}/${dateTime.monthOfYear().get()}/${dateTime.year().get()}"
    }
}

private fun EventModel.salesStatus(todaysDate: DateTime): SaleStatus {
    if (presales.isOnPresale(todaysDate)) return SaleStatus.PRESALE
    if (publicSales.isSaleDateAnnounced()) {
        return if (publicSales.isOnPublicSale(todaysDate)) SaleStatus.SALE
        else SaleStatus.COMING_SOON
    }

    return SaleStatus.UNKNOWN
}

private fun List<PresalesModel>?.isOnPresale(todaysDate: DateTime): Boolean {
    return this?.any { presale -> todaysDate.isBetween(presale.startDateTime, presale.endDateTime) }
        ?: false
}

private fun PublicModel?.isSaleDateAnnounced(): Boolean {
    return this == null || (!startTBA && !startTBD)
}

private fun PublicModel?.isOnPublicSale(todaysDate: DateTime): Boolean {
    return if (this == null) false else {
        todaysDate.isBetween(startDateTime, endDateTime)
    }
}

private fun DateTime.isBetween(start: String?, end: String?): Boolean {
    return if (start == null || end == null) false else {
        val startDate = DateTime(start)
        val endDate = DateTime(end)
        isAfter(startDate) && isBefore(endDate)
    }
}