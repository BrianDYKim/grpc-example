import com.google.protobuf.gradle.*

val grpcVersion = "3.19.4"
val grpcProtoVersion = "1.44.1"
val grpcKotlinVersion = "1.2.1" // kotlin stub 생성 버전

apply(plugin = "com.google.protobuf")

dependencies {
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")

    // Kotlin Standard Library
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
}

// build 이후에 stub class가 생성되는 위치를 target으로 추가하기 위한 설정
// 해당 설정을 통해서 소스 내에서 stub class를 참조 가능하다
sourceSets {
    getByName("main") {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin"
            )
        }
    }
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:$grpcVersion" }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}