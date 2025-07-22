plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"
extra["keycloakVersion"] = "25.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // zipkin //
    // Micrometer Tracing (замена Sleuth)
    implementation("io.micrometer:micrometer-tracing") // Основная зависимость
    implementation("io.micrometer:micrometer-tracing-bridge-brave") // Мост для OpenTelemetry (опционально)

    // Zipkin Reporter (если нужен экспорт трейсов в Zipkin)
    implementation("io.zipkin.reporter2:zipkin-reporter-brave") // Brave (ранее использовался в Sleuth)
    implementation("net.logstash.logback:logstash-logback-encoder:8.1") // 8.1
    // //

    // kafka
    //	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    implementation("org.keycloak:keycloak-spring-boot-starter:${property("keycloakVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.keycloak.bom:keycloak-adapter-bom:${property("keycloakVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}