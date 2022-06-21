package net.wedocode.chatopsdemo.messages.commands

sealed class AccountCommands {
    data class RequestCreateAccount(
        val accountId: Int,
        val accountName: String,
        val accountDescription: String
    )
}