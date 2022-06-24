package net.wedocode.chatopsdemo.messages.events

import net.wedocode.chatopsdemo.messages.domainTypes.AccountCreationStatus


sealed class AccountEvents {
    data class AccountCreateRequested(
        val accountId: String,
        val accountName: String,
        val accountDescription: String,
        val accountStatus: AccountCreationStatus = AccountCreationStatus.INITIALIZING
    )

    data class BankingPartnerSetup(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )

    data class AccountCreationPaused(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )

    data class AccountCreationResumed(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )

    data class AccountCreationCompleted(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )


}