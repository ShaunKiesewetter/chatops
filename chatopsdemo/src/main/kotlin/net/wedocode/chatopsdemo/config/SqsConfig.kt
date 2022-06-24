package net.wedocode.chatopsdemo.config

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.sns.AmazonSNSAsync
import com.amazonaws.services.sns.AmazonSNSAsyncClient
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.amazonaws.util.json.Jackson
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.messaging.converter.MappingJackson2MessageConverter

@Configuration
@EnableConfigurationProperties(AppAWSConfig::class)
class SqsConfig {

    @Autowired
    private lateinit var awsConfig: AppAWSConfig

    @Autowired
    private lateinit var jackson2ObjectMapperBuilderCustomizer: Jackson2ObjectMapperBuilderCustomizer

    @Autowired
    private lateinit var jackson2ObjectMapperBuilder: Jackson2ObjectMapperBuilder

    @Bean
    @Primary
    fun amazonSNSClient(): AmazonSNSAsync {
        val config = ClientConfiguration()
        return AmazonSNSAsyncClient.asyncBuilder().withClientConfiguration(config)
            .withRegion(Regions.fromName(awsConfig.region.static))
            .withCredentials(
                AWSBasicCredentialsProvider(
                    awsConfig.credentials.accessKey,
                    awsConfig.credentials.secretKey
                )
            )
            .build()
    }

    @Bean
    @Primary
    fun amazonSQSAsync(): AmazonSQSAsync? {
        return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.fromName(awsConfig.region.static))
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        awsConfig.credentials.accessKey,
                        awsConfig.credentials.secretKey
                    )
                )
            )
            .build()
    }


    @Bean
    fun queueMessageHandlerFactory(): QueueMessageHandlerFactory {
        val queueMessageHandlerFactory = QueueMessageHandlerFactory()
        queueMessageHandlerFactory.messageConverters = listOf(converter())
        return queueMessageHandlerFactory
    }

    private fun converter(): MappingJackson2MessageConverter {
        jackson2ObjectMapperBuilderCustomizer.customize(jackson2ObjectMapperBuilder)
        val converter = MappingJackson2MessageConverter()
        converter.serializedPayloadClass = String::class.java
        converter.objectMapper = jackson2ObjectMapperBuilder.build()
        return converter
    }

    private class AWSBasicCredentialsProvider(val accessKeyID: String, val secretAccessKey: String) :
        AWSCredentialsProvider {
        override fun getCredentials(): AWSCredentials {
            return BasicAWSCredentials(accessKeyID, secretAccessKey)
        }

        override fun refresh() {
        }
    }
}