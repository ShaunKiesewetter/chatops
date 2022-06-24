package net.wedocode.chatopsdemo.messages.commands

import net.wedocode.chatopsdemo.messages.domainTypes.AccountCreationStatus

sealed class AccountCommands {
    data class RequestCreateAccount(
        val accountId: String,
        val accountName: String,
        val accountDescription: String
    )

    data class SetupBankingPartner(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )

    data class PauseWorkflow(
        val accountId: String,
        val accountCreationStatus: AccountCreationStatus
    )

    data class ContinueAccountCreation(
        val accountId: String,

    )

    data class CompleteSuccess(
        val accountId: String
    )
}