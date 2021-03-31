package info.tommarsh.eventsearch.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import info.tommarsh.eventsearch.core.notifications.channel.EventReminderChannel
import info.tommarsh.eventsearch.core.notifications.channel.NotificationChannelFactory

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationChannelModule {

    @Binds
    @IntoSet
    abstract fun bindEventReminderChannel(eventReminderChannel: EventReminderChannel): NotificationChannelFactory
}