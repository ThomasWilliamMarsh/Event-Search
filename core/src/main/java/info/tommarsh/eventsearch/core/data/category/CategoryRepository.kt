package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.core.data.category.model.domain.CategoryModel

interface CategoryRepository {

    fun getCategories(): List<CategoryModel>

}