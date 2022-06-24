package net.wedocode.chatopsdemo.adapters

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import net.wedocode.chatopsdemo.messages.commands.AccountCommands
import net.wedocode.chatopsdemo.ports.SlackMessageSender
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.GsonBuilderUtils
import org.springframework.stereotype.Component

@Component
class SlackMessageHandler  {

    @Autowired
    lateinit var slackMessageSender: SlackMessageSender

    @Autowired
    lateinit var commandGateWay: CommandGateway

    @SqsListener("banking-ops-command-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun handleSlackCommands(message: String){

        println("Processing message: $message")

        // this is hack week ... dirty as hell but deserialize until the message fits
        try {
            val gson = GsonBuilder().create()
            val requestCreateAccountCommand = gson.fromJson(message, AccountCommands.RequestCreateAccount::class.java)
            slackMessageSender.publishNotification("Banking service processing request for create account with payload \n AccountID = ${requestCreateAccountCommand.accountId}")
            commandGateWay.sendAndWait<AccountCommands.RequestCreateAccount>(requestCreateAccountCommand)
            return
        } catch (ex: Exception) {
            slackMessageSender.publishNotification("Error processing request: \n Message: ${ex.message}")
        }


        println(message)
    }
}