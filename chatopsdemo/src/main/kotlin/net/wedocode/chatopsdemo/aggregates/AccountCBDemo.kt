package net.wedocode.chatopsdemo.aggregates

import net.wedocode.chatopsdemo.messages.domainTypes.AccountCreationStatus
import net.wedocode.chatopsdemo.messages.events.AccountEvents
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply


@Aggregate(repository = "accountAggregateRepository")
class AccountCBDemo {



    @AggregateIdentifier
    lateinit var accountId: String
    lateinit var accountName: String
    lateinit var accountDescription: String
    lateinit var accountCreationStatus: AccountCreationStatus

    constructor()

    constructor(
        accountId: String,
        accountName: String,
        accountDescription: String
    ){
        println("Aggregate constructor preparing to apply event AccountID = $accountId")
        apply(
            AccountEvents.AccountCreateRequested(
                accountId,
                accountName,
                accountDescription,
                AccountCreationStatus.INITIALIZING
            )
        )
    }

    fun continueCreatingAccount(accountId: String) {
        println("Resuming account creation for = $accountId")
        apply(
            AccountEvents.AccountCreateRequested(
                accountId,
                accountName,
                accountDescription,
                AccountCreationStatus.RESUMING
            ))
    }

    @EventSourcingHandler
    fun on(event: AccountEvents.AccountCreateRequested){
        println("Event sourcing Account Create Requested")
        accountId = event.accountId
        accountName = event.accountName
        accountDescription = event.accountDescription
        accountCreationStatus = event.accountStatus
    }



    fun setupBankingPartner(accountId: String){
        apply(
            AccountEvents.BankingPartnerSetup(
                accountId,
                AccountCreationStatus.SETTING_UP_PARTNER
            )
        )
    }

    @EventSourcingHandler
    fun on(event: AccountEvents.BankingPartnerSetup){
        accountCreationStatus = event.accountCreationStatus
    }

    fun pauseWorkflow(accountId: String) {
        apply(
            AccountEvents.AccountCreationPaused(
                accountId,
                AccountCreationStatus.WAITING_FOR_ACTION
            )
        )
    }

    @EventSourcingHandler
    fun on(event: AccountEvents.AccountCreationPaused){
        accountCreationStatus = event.accountCreationStatus
    }

    fun completeSuccess(accountId: String) {
        apply(
            AccountEvents.AccountCreationCompleted(
                accountId,
                AccountCreationStatus.SUCCESSFUL
            )
        )
    }

    @EventSourcingHandler
    fun on(event: AccountEvents.AccountCreationCompleted){
        accountCreationStatus = event.accountCreationStatus
    }

    fun resumeAccount(accountId: String) {
        apply(
            AccountEvents.AccountCreationResumed(
                accountId,
                AccountCreationStatus.RESUMING
            )
        )
    }

    @EventSourcingHandler
    fun on(event: AccountEvents.AccountCreationResumed){
        accountCreationStatus = event.accountCreationStatus
    }




}