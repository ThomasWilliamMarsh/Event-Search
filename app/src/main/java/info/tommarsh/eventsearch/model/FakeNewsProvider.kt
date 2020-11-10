import info.tommarsh.eventsearch.model.AttractionViewModel
import info.tommarsh.eventsearch.model.CategoryViewModel

internal val musicCategory = CategoryViewModel(id = "", name = "Music")
internal val sportCategory = CategoryViewModel(id = "", name = "Sport")
internal val artCategory = CategoryViewModel(id = "", name = "Arts")
internal val familyCategory = CategoryViewModel(id = "", name = "Family & Attractions")

internal val attraction = AttractionViewModel(
    name = "The Book of Mormon",
    id = "123",
    type = "event",
    url = "https://www.ticketmaster.co.uk/event/09005745E5F54CFA",
    searchImage = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png",
    detailImage = "https://bookofmormonbroadway.com/images/responsive/mobile/title-treatment-alt-nosp.png",
    locale = "en-us",
    genre = "Theatre",
    numberOfEvents = 20
)