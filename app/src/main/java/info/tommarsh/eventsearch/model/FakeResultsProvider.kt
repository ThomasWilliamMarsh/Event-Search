package info.tommarsh.eventsearch.model

val onSaleEvent = EventViewModel(
    "id",
    "Grupo Firme",
    "STAPLES Center",
    "14/05/2020",
    "Live Nation Music",
    SaleStatus.SALE,
    "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg"
)

val comingSoonEvent = EventViewModel(
    "id",
    "Grupo Firme",
    "STAPLES Center",
    "",
    "Live Nation Music",
    SaleStatus.COMING_SOON,
    "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg"
)

val presaleEvent = EventViewModel(
    "id",
    "Grupo Firme",
    "STAPLES Center",
    "14/07/2020",
    "Live Nation Music",
    SaleStatus.PRESALE,
    "https://s1.ticketm.net/dam/c/f50/96fa13be-e395-429b-8558-a51bb9054f50_105951_TABLET_LANDSCAPE_LARGE_16_9.jpg"
)

val musicCategory = CategoryViewModel(id = "", name = "Music")
val sportCategory = CategoryViewModel(id = "", name = "Sport")
val artCategory = CategoryViewModel(id = "", name = "Arts")
val familyCategory = CategoryViewModel(id = "", name = "Family & Attractions")