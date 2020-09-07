package info.tommarsh.eventsearch.core.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import info.tommarsh.eventsearch.core.data.EventRepository
import info.tommarsh.eventsearch.core.data.EventRepositoryImpl
import info.tommarsh.eventsearch.core.data.model.EventResponse
import info.tommarsh.eventsearch.core.data.remote.EventsAPI
import info.tommarsh.eventsearch.core.data.remote.EventsAPIInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
internal object DataModule {

    private const val BASE_URL = "https://app.ticketmaster.com/"

    @Provides
    fun bindEventRepository(repository: EventRepositoryImpl): EventRepository =
        repository

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideEventResponseDeserialiser(moshi: Moshi): JsonAdapter<EventResponse> {
        return moshi
            .adapter(EventResponse::class.java)
    }

    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(EventsAPIInterceptor())
            .build()
    }

    @Provides
    fun provideEventsAPI(
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
}