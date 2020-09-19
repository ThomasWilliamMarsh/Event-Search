package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.domain.CategoryModel

interface CategoryRepository {

    fun getCategories() : List<CategoryModel>

}