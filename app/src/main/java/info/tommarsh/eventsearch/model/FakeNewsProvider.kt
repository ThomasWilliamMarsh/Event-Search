import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel
import info.tommarsh.eventsearch.model.EventDateViewModel
import info.tommarsh.eventsearch.model.EventViewModel

internal val musicCategory = CategoryViewModel(id = "", name = "Music")
internal val sportCategory = CategoryViewModel(id = "", name = "Sport")
internal val artCategory = CategoryViewModel(id = "", name = "Arts")
internal val familyCategory = CategoryViewModel(id = "", name = "Family & Attractions")

internal val attraction = AttractionViewModel(
    name = "The Book of Mormon",
    id = "123",
    description = "Great fun for all the family... maybe",
    type = "event",
    url = "https://www.ticketmaster.co.uk/event/09005745E5F54CFA",
    searchImage = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png",
    detailImage = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png",
    locale = "en-us",
    genre = "Theatre",
    numberOfEvents = 20
)

internal val attractionDetail = AttractionDetailsViewModel(
    id = "123",
    name = "The Book of Mormon",
    genre = "Theatre",
    description = "Great fun for all the family... maybe",
    numberOfEvents = 1,
    detailImage = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png",
    events = listOf(
        EventViewModel(
            date = EventDateViewModel.Date("Feb", "05", "Friday - 11:00"),
            venue = "Arrowhead Stadium"
        )
    )
)