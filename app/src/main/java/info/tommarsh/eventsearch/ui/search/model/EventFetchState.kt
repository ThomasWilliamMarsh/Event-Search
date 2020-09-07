package info.tommarsh.eventsearch.ui.search.model

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
sealed class EventFetchState {
    object Loading : EventFetchState()
    data class Success(val events: List<EventViewModel>) : EventFetchState()
    data class Failure(val throwable: Throwable) : EventFetchState()
}