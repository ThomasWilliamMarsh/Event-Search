package info.tommarsh.eventsearch.ui.search.model

import info.tommarsh.eventsearch.domain.EventModel
import info.tommarsh.eventsearch.domain.PresalesModel
import info.tommarsh.eventsearch.domain.PublicModel
import org.joda.time.DateTime
import java.util.*

fun List<info.tommarsh.eventsearch.domain.EventModel>.toViewModel(): List<EventViewModel> {
    val todaysDate = DateTime(Date().time)

    return map { domain ->
        EventViewModel(
            id = domain.id,
            name = domain.name,
            venue = domain.venue,
            dates = domain.initialStartDateTime.toDateString(),
            imageUrl = domain.imageUrl,
            saleStatus = domain.salesStatus(todaysDate)
        )
    }
}

private fun String?.toDateString(): String {
    return if (this == null) "" else {
        val dateTime = DateTime(this)
        "${dateTime.dayOfMonth().get()}/${dateTime.monthOfYear().get()}/${dateTime.year().get()}"
    }
}

private fun info.tommarsh.eventsearch.domain.EventModel.salesStatus(todaysDate: DateTime): SaleStatus {
    if (presales.isOnPresale(todaysDate)) return SaleStatus.PRESALE
    if (publicSales.isSaleDateAnnounced()) {
        return if (publicSales.isOnPublicSale(todaysDate)) SaleStatus.SALE
        else SaleStatus.COMING_SOON
    }

    return SaleStatus.UNKNOWN
}

private fun List<info.tommarsh.eventsearch.domain.PresalesModel>?.isOnPresale(todaysDate: DateTime): Boolean {
    return this?.any { presale -> todaysDate.isBetween(presale.startDateTime, presale.endDateTime) }
        ?: false
}

private fun info.tommarsh.eventsearch.domain.PublicModel?.isSaleDateAnnounced(): Boolean {
    return this == null || (!startTBA && !startTBD)
}

private fun info.tommarsh.eventsearch.domain.PublicModel?.isOnPublicSale(todaysDate: DateTime): Boolean {
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
