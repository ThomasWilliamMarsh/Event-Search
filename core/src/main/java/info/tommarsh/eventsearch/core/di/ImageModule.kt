package info.tommarsh.eventsearch.core.di

import android.content.Context
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    fun provideImageLoader(@ApplicationContext context: Context) : ImageLoader {
        return ImageLoader.invoke(context)
    }
}