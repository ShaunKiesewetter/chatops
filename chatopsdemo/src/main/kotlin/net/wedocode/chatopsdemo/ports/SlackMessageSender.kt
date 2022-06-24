package net.wedocode.chatopsdemo.ports

interface SlackMessageSender {
    fun publishNotification(message: String)
}