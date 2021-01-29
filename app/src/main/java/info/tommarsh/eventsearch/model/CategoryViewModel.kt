package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.core.data.category.model.domain.CategoryModel

internal data class CategoryViewModel(
    val id: String,
    val name: String
)

internal fun List<CategoryModel>.toViewModel(): List<CategoryViewModel> {
    return map { domain ->
        CategoryViewModel(
            id = domain.id,
            name = domain.name
        )
    }
}