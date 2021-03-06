package info.tommarsh.eventsearch.core.data.category

import info.tommarsh.eventsearch.core.data.category.model.data.CategoryResponse
import info.tommarsh.eventsearch.core.data.category.model.domain.CategoryModel
import info.tommarsh.eventsearch.core.data.category.model.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override fun getCategories(): Flow<List<CategoryModel>> {
        return flowOf(
            listOf(
                CategoryResponse(id = "Music", name = "Music"),
                CategoryResponse(id = "Sports", name = "Sports"),
                CategoryResponse(id = "Arts", name = "Arts"),
                CategoryResponse(id = "Arts & Theatre", name = "Arts, Theatre & Comedy"),
                CategoryResponse(id = "Family", name = "Family & Attractions")
            ).toDomainModel()
        )
    }
}