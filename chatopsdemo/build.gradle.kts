import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "net.wedocode"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}


allprojects {
	dependencyManagement{
		imports{
			mavenBom("org.axonframework:axon-bom:4.5.12")
			mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:2.3.3")
		}

		dependencies{
			dependency("org.postgresql:postgresql:42.3.3")
		}
//		dependencies {
//			dependencySet("org.springframework.cloud:2.2.6.RELEASE") {
//				entry("spring-cloud-starter-aws")
//				entry("spring-cloud-starter-aws-messaging")
//			}
//		}
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.axonframework:axon-spring-boot-starter")

	implementation("io.awspring.cloud:spring-cloud-starter-aws")
	implementation("io.awspring.cloud:spring-cloud-starter-aws-messaging")
	implementation("io.awspring.cloud:spring-cloud-starter-aws")

	// Validation
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("org.hibernate:hibernate-validator:5.2.4.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Serialization and Deserialization
	implementation("com.google.code.gson:gson:2.8.+")

	//Tracking Service tokens
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
