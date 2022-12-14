rootProject.name = "grpc-example"

pluginManagement {
    val kotlinVersion: String = "1.6.21"
    val springBootVersion: String = "2.7.3"
    val springDependencyManagementVersion: String = "1.0.13.RELEASE"
    val protobufVersion = "0.8.13"

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion

        id("com.google.protobuf") version protobufVersion
    }
}

include("grpc-interface")
include("client")
include("server")