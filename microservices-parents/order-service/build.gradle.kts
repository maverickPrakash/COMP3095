plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "ca.gbc"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.projectlombok:lombok:1.18.22")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.5")
	implementation("org.springframework:spring-web:6.0.13")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.3")
	testImplementation("org.testcontainers:testcontainers:1.16.0")
	testImplementation("org.testcontainers:postgresql:1.16.0")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
	testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:3.0.1")
	implementation (platform("org.testcontainers:testcontainers-bom:1.18.3"))
	testImplementation("org.testcontainers:junit-jupiter")
	implementation ("org.springframework.boot:spring-boot-starter-webflux:3.1.5")
	implementation ("org.apache.httpcomponents.client5:httpclient5:5.2.1")
	testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
