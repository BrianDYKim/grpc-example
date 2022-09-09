rootProject.name = "grpc-example"

pluginManagement {
    val kotlinVersion: String = "1.6.21"
    val springBootVersion: String = "2.7.3"
    val springDependencyManagementVersion: String = "1.0.13.RELEASE"

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}

include("independent")
include("independent:grpc-interface")
include("client")
include("server")