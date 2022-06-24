package net.wedocode.chatopsdemo.messages.domainTypes

enum class AccountCreationStatus {
    INITIALIZING,
    SETTING_UP_PARTNER,
    RESUMING,
    WAITING_FOR_ACTION,
    UNSUCCESSFUL,
    SUCCESSFUL
}