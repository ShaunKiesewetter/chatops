package net.wedocode.chatopsdemo.config

import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated


@ConfigurationProperties("cloud.aws")
@ConstructorBinding
class AppAWSConfig {


    @NotNull
    var credentials: Credentials = Credentials()


    @NotNull
    var region = Region()

    @ConstructorBinding
    class Region {
        var static: String = ""
    }

    @ConstructorBinding
    class Credentials {

        @NotNull
        var accessKey: String = ""


        @NotNull
        var secretKey: String = ""
    }
}