package net.wedocode.chatopsdemo.adapters

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSAsync
import com.amazonaws.services.sns.AmazonSNSClient
import net.wedocode.chatopsdemo.ports.SlackMessageSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SlackMessagePublisher: SlackMessageSender {

    @Autowired
    lateinit var snsClient: AmazonSNSAsync

     override fun publishNotification(message: String){
        snsClient.publish("arn:aws:sns:us-east-1:354732697202:Banking-Ops-Notification", message)
    }

}