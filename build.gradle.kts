plugins {
    java
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.sonarqube") version "3.0"
    id("jacoco")
}

group = "com.b2"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
    implementation ("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation(group = "io.jsonwebtoken", name = "jjwt-api", version = "0.11.5")
    runtimeOnly(group = "io.jsonwebtoken", name = "jjwt-impl", version = "0.11.5")
    runtimeOnly(group = "io.jsonwebtoken", name = "jjwt-jackson", version = "0.11.5")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-jre")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonarqube{
    properties{
        property("sonar.projectKey", "AdvProg_reguler-2023_mahasiswa_kelas-b_2106750231-Jaycent-Gunawan-Ongris-_kelompok-b2_auth_AYbzMI2FSUPdlmizh9d7")
        property("sonar.host.url", "https://sonarqube.cs.ui.ac.id")
        property("sonar.login", "f726ad20740716890ba788f261c78f242c54611d")
    }
}