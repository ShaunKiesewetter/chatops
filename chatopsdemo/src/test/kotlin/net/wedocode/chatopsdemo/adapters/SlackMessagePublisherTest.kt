package net.wedocode.chatopsdemo.adapters

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SlackMessagePublisherTest{

    @Autowired
    lateinit var slackMessagePublisher: SlackMessagePublisher

    @Test
    fun `Test sending message to SNS Topic` (){
        slackMessagePublisher.publishNotification("This is a test message")
    }
}