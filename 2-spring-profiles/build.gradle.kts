plugins {
    id("java")
    id("org.springframework.boot") version("3.3.3")
    id("io.spring.dependency-management") version("1.1.6")
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
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Json assertj assertions
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.0.0")
    // apache text utilities for StringSubstitutor
    testImplementation("org.apache.commons:commons-text:1.10.0")
    testImplementation("org.wiremock:wiremock-standalone:3.9.1")
}

tasks.test {
    useJUnitPlatform()
}