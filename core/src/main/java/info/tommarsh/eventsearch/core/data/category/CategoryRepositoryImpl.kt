package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.core.data.category.model.CategoryResponse
import info.tommarsh.eventsearch.core.data.category.model.toDomainModel
import info.tommarsh.eventsearch.domain.CategoryModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override fun getCategories(): List<CategoryModel> {
        return listOf(
                CategoryResponse(id = "", name = "Music"),
                CategoryResponse(id = "", name = "Sport"),
                CategoryResponse(id = "", name = "Arts"),
                CategoryResponse(id = "", name = "Arts, Theatre & Comedy"),
                CategoryResponse(id = "", name = "Family & Attractions")
        ).toDomainModel()
    }
}