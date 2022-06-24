package net.wedocode.chatopsdemo.sagas

import net.wedocode.chatopsdemo.messages.commands.AccountCommands
import net.wedocode.chatopsdemo.messages.domainTypes.AccountCreationStatus
import net.wedocode.chatopsdemo.messages.events.AccountEvents
import net.wedocode.chatopsdemo.ports.SlackMessageSender
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.springframework.beans.factory.annotation.Autowired


@Saga
class AccountCreationWorkflow {
    @Transient
    @Autowired
    lateinit var slackMessageSender: SlackMessageSender

    @Transient
    @Autowired
    lateinit var commandGateway: CommandGateway

    lateinit var accountId: String
    lateinit var accountName: String
    lateinit var accountDescription: String

    @StartSaga
    @SagaEventHandler(associationProperty = "accountId")
    fun on(createAccountRequested: AccountEvents.AccountCreateRequested){
        accountId = createAccountRequested.accountId
        accountName = createAccountRequested.accountName
        accountDescription = createAccountRequested.accountDescription

        when (createAccountRequested.accountStatus){
            AccountCreationStatus.INITIALIZING -> commandGateway.send<AccountCommands>(
                AccountCommands.SetupBankingPartner(accountId, AccountCreationStatus.SETTING_UP_PARTNER)
            )

            AccountCreationStatus.RESUMING -> commandGateway.send<AccountCommands>(
                AccountCommands.ContinueAccountCreation(accountId))

            else -> throw Exception("Invalid operation")
        }
    }

    // Typically, an error occurs in this event handler which will cause us to pause the workflow.
    @SagaEventHandler(associationProperty = "accountId")
    fun on(setupBankingPartner: AccountEvents.BankingPartnerSetup){
        // for some reason the banking partner service provider seems to be down ....
        slackMessageSender.publishNotification("Banking Partner service is not available. \n Please contact them and investigate. \n" +
                "After the issue is resolved you can resume this workflow with the following command: \n \n" +
                "@aws lambda invoke --payload {\"accountId\":\"$accountId\", \"accountName\":\"$accountName\", \"accountDescription\":\"$accountDescription\"} --function-name Create-Account --chatbot-replace-curly-quotes enable --region us-east-1")

        commandGateway.send<AccountCommands.PauseWorkflow>(AccountCommands.PauseWorkflow(accountId, AccountCreationStatus.WAITING_FOR_ACTION))

    }

    @SagaEventHandler(associationProperty = "accountId")
    fun on(accountCreationResume: AccountEvents.AccountCreationResumed){
        // Would typically try to setup banking partner again then resume
        // for now we just resume
        slackMessageSender.publishNotification("Saga resuming for accountId ${accountCreationResume.accountId}")
        commandGateway.send<AccountCommands.CompleteSuccess>(AccountCommands.CompleteSuccess(accountId))
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "accountId")
    fun on(accountCreationCompleted: AccountEvents.AccountCreationCompleted){
        slackMessageSender.publishNotification("Account creation completed well done sun shine !!!!")
    }




}