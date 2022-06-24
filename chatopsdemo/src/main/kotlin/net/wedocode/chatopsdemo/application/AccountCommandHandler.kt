package net.wedocode.chatopsdemo.application

import net.wedocode.chatopsdemo.aggregates.AccountCBDemo
import net.wedocode.chatopsdemo.messages.commands.AccountCommands
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.modelling.command.Repository
import org.springframework.stereotype.Component

@Component
class AccountCommandHandler(
    private val accountCBDemoAggregateRepository: Repository<AccountCBDemo>,
) {

    @CommandHandler
    fun handle(command: AccountCommands.RequestCreateAccount) {
        try {
            println("Received domain message to open account ")
            accountCBDemoAggregateRepository.load(command.accountId).invoke {
                it.continueCreatingAccount(command.accountId)
            }
        } catch (e: AggregateNotFoundException) {
            println("Creating new account aggregate")
            accountCBDemoAggregateRepository.newInstance {
                AccountCBDemo(
                    command.accountId,
                    command.accountName,
                    command.accountDescription
                )
            }
        }
    }

    @CommandHandler
    fun handle(command: AccountCommands.SetupBankingPartner){
        accountCBDemoAggregateRepository.load(command.accountId).invoke {
            it.setupBankingPartner(command.accountId)
        }
    }

    @CommandHandler
    fun handle(command: AccountCommands.PauseWorkflow){
        accountCBDemoAggregateRepository.load(command.accountId).invoke {
            it.pauseWorkflow(command.accountId)
        }
    }


    @CommandHandler
    fun handle(command: AccountCommands.CompleteSuccess){
        accountCBDemoAggregateRepository.load(command.accountId).invoke {
            it.completeSuccess(command.accountId)
        }
    }

    @CommandHandler
    fun handle(command: AccountCommands.ContinueAccountCreation){
        accountCBDemoAggregateRepository.load(command.accountId).invoke {
            it.resumeAccount(command.accountId)
        }
    }



}