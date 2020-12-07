package info.tommarsh.eventsearch.core.di

import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room.databaseBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCase
import info.tommarsh.eventsearch.core.data.AttractionDetailsUseCaseImpl
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepositoryImpl
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsAPI
import info.tommarsh.eventsearch.core.data.attractions.remote.AttractionsAPIInterceptor
import info.tommarsh.eventsearch.core.data.category.CategoryRepository
import info.tommarsh.eventsearch.core.data.category.CategoryRepositoryImpl
import info.tommarsh.eventsearch.core.data.events.EventRepository
import info.tommarsh.eventsearch.core.data.events.EventRepositoryImpl
import info.tommarsh.eventsearch.core.data.events.remote.EventsAPI
import info.tommarsh.eventsearch.core.data.likes.LikesRepository
import info.tommarsh.eventsearch.core.data.likes.LikesRepositoryImpl
import info.tommarsh.eventsearch.core.data.likes.local.LikedAttractionsDao
import info.tommarsh.eventsearch.core.data.likes.local.LikesDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal object DataModule {

    private const val BASE_URL = "https://app.ticketmaster.com/"

    @Provides
    fun bindAttractionRepository(repository: AttractionsRepositoryImpl): AttractionsRepository =
        repository

    @Provides
    fun bindEventRepository(repository: EventRepositoryImpl): EventRepository = repository

    @Provides
    fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository =
        repository

    @Provides
    fun bindAttractionDetailsUseCase(impl: AttractionDetailsUseCaseImpl): AttractionDetailsUseCase =
        impl

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AttractionsAPIInterceptor())
            .build()
    }

    @Provides
    fun provideAttractionsAPI(
        moshi: Moshi,
        client: OkHttpClient
    ): AttractionsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AttractionsAPI::class.java)
    }

    @Provides
    fun provideEventAPI(
        moshi: Moshi,
        client: OkHttpClient
    ): EventsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EventsAPI::class.java)
    }

    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            initialLoadSize = 20,
            pageSize = 20
        )
    }

    @Provides
    @Singleton
    fun provideLikesDatabase(@ApplicationContext context: Context): LikesDatabase {
        return databaseBuilder(context, LikesDatabase::class.java, "likesDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLikedAttractionsDao(db: LikesDatabase): LikedAttractionsDao {
        return db.likedAttractionsDao()
    }

    @Provides
    fun provideLikedRepository(impl: LikesRepositoryImpl): LikesRepository = impl
}