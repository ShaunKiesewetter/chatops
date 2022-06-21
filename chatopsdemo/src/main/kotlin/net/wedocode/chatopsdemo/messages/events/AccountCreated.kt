package net.wedocode.chatopsdemo.messages.events


sealed class AccountEvents {
    data class AccountCreateRequested(
        val accountId: Int,
        val accountName: String,
        val accountDescription: String
    )
}