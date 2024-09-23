import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins{
    java
    id("org.springframework.boot") version("3.3.3")
    id("io.spring.dependency-management") version("1.1.6")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":news"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
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