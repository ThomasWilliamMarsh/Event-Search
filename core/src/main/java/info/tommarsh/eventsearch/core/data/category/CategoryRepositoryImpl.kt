package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.core.data.category.model.CategoryResponse
import info.tommarsh.eventsearch.core.data.category.model.toDomainModel
import info.tommarsh.eventsearch.domain.CategoryModel
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override fun getCategories(): List<CategoryModel> {
        return listOf(
                CategoryResponse(id = "music", name = "Music"),
                CategoryResponse(id = "sport", name = "Sport"),
                CategoryResponse(id = "arts", name = "Arts"),
                CategoryResponse(id = "atc", name = "Arts, Theatre & Comedy"),
                CategoryResponse(id = "faa", name = "Family & Attractions")
        ).toDomainModel()
    }
}