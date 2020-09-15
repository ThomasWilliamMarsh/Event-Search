package info.tommarsh.eventsearch.core.data.category.model

import info.tommarsh.eventsearch.domain.CategoryModel

fun List<CategoryResponse>.toDomainModel() : List<CategoryModel> {
    return map { response ->
        CategoryModel(
                id = response.id,
                name = response.name
        )
    }
}