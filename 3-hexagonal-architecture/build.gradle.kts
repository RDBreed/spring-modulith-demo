plugins {
    id("java")
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "eu.phaf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":news"))
    implementation(project(":news-import"))
    implementation(project(":weather"))
    implementation(project(":location"))
    implementation(project(":user"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-webtestclient")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Json assertj assertions
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:5.1.0")
    // apache text utilities for StringSubstitutor
    testImplementation("org.apache.commons:commons-text:1.14.0")
    testImplementation("org.wiremock:wiremock-standalone:3.13.2")
}

tasks.test {
    useJUnitPlatform()
}