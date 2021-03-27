package info.tommarsh.eventsearch.ui.attractions.model

import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.AttractionDetailsViewModel
import info.tommarsh.eventsearch.model.FetchState

internal data class AttractionDetailScreenState(
    val isLiked: Boolean = false,
    val fetchState: FetchState<AttractionDetailsViewModel> = FetchState.Loading(true)
)

internal sealed class AttractionDetailScreenAction {
    data class FetchDetails(val id: String) : AttractionDetailScreenAction()
    data class ClickLiked(val attraction: LikedAttractionModel) : AttractionDetailScreenAction()
}