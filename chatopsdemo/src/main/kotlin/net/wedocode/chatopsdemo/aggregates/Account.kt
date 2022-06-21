package net.wedocode.chatopsdemo.aggregates

import net.wedocode.chatopsdemo.messages.events.AccountEvents
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply


@Aggregate(repository = "accountAggregateRepository")
class Account {
    @AggregateIdentifier
    var accountId: Int = 0

    constructor()

    constructor(
        accountId: Int,
        accountName: String,
        accountDescription: String
    ){
        apply(
            AccountEvents.AccountCreateRequested(
                accountId,
                accountName,
                accountDescription
            )
        )


    }
}