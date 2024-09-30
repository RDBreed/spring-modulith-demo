import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    // we are going to need to add api below, see below ;)
    `java-library`
    id("org.springframework.boot") version ("3.3.3")
    id("io.spring.dependency-management") version ("1.1.6")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    // spring-data-starter-jpa would include these - and some more:
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("com.h2database:h2")
    // since we (still) test in the main module with jpa & are using the repo in news-import, we need to allow this dependency to be used
    // clean? probably not! Let's check what we can do in another example.
    api("org.springframework.data:spring-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}