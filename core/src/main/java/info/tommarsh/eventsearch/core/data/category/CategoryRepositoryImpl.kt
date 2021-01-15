package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.core.data.category.model.CategoryResponse
import info.tommarsh.eventsearch.core.data.category.model.toDomainModel
import info.tommarsh.eventsearch.domain.CategoryModel
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override fun getCategories(): List<CategoryModel> {
        return listOf(
                CategoryResponse(id = "Music", name = "Music"),
                CategoryResponse(id = "Sport", name = "Sport"),
                CategoryResponse(id = "Arts", name = "Arts"),
                CategoryResponse(id = "Arts & Theatre", name = "Arts, Theatre & Comedy"),
                CategoryResponse(id = "Family & Attractions", name = "Family & Attractions")
        ).toDomainModel()
    }
}