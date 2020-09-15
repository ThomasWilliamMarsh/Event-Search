package info.tommarsh.eventsearch.ui.search.model

import info.tommarsh.eventsearch.domain.CategoryModel

data class CategoryViewModel(
    val id: String,
    val name: String
)

fun List<CategoryModel>.toViewModel(): List<CategoryViewModel> {
    return map { domain ->
        CategoryViewModel(
            id = domain.id,
            name = domain.name
        )
    }
}