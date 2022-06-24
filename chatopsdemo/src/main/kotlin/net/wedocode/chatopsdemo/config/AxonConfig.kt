package net.wedocode.chatopsdemo.config

import com.thoughtworks.xstream.XStream
import net.wedocode.chatopsdemo.aggregates.AccountCBDemo
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {
    @Bean
    fun accountAggregateRepository(eventStore: EventStore): Repository<AccountCBDemo> {
        return EventSourcingRepository.builder(AccountCBDemo::class.java).eventStore(eventStore).build()
    }

    // Relates to https://discuss.axoniq.io/t/getting-xstream-dependency-exception/3634/4
    // Follow up with Axon support as this work-around simply opens the door if there was valid attack vector from our position.
    @Bean
    fun xStream(): XStream {
        val xStream = XStream()

        xStream.allowTypesByWildcard(
            arrayOf(
                // Beef up security if need be by allowing types to be deserialized.
                "**",
            )
        )
        return xStream
    }
}
