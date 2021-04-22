package info.tommarsh.eventsearch.attraction.ui.model

import info.tommarsh.eventsearch.core.data.FetchState
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel

internal data class AttractionDetailScreenState(
    val isLiked: Boolean = false,
    val fetchState: FetchState<AttractionDetailsViewModel> = FetchState.Loading(true)
)

internal sealed class AttractionDetailScreenAction {
    data class ClickLiked(val attraction: LikedAttractionModel) : AttractionDetailScreenAction()
}