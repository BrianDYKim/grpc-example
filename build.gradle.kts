import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("com.google.protobuf")
}

allprojects {
    group = "com.brian"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

// commons 내부의 모듈을 제외하고 공통된 의존을 넣어준다
val nonDependentProjects = listOf("independent")
configure(subprojects.filter { it.name !in nonDependentProjects }) {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

    val coroutineVersion = "1.6.3"
    val mockkVersion = "1.12.0"
    val kotestVersion = "5.3.2"

    dependencies {
        // Kotlin Standard Library
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Jackson
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.module:jackson-module-afterburner")

        // Kotlin Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutineVersion")

        // Spring Boot Dependencies
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        // Test Implementation
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        // mockk
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion") // for kotest framework
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion") // for kotest core jvm assertions
        testImplementation("io.kotest:kotest-property:$kotestVersion") // for kotest property test
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")

        // Annotation Processing Tool
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "16"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}