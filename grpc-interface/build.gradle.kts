import com.google.protobuf.gradle.*

apply(plugin = "com.google.protobuf")

val grpcVersion = "3.19.4"
val grpcProtoVersion = "1.44.1"
val grpcKotlinVersion = "1.2.1" // kotlin stub 생성 버전
val grpcJavaUtilVersion = "3.14.0"

configurations.forEach {
    if (it.name.toLowerCase().contains("proto")) {
        it.attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, "java-runtime"))
    }
}

dependencies {
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

    implementation("com.google.protobuf:protobuf-java-util:$grpcJavaUtilVersion")
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-netty-shaded:$grpcProtoVersion")
}

// build 이후에 stub class가 생성되는 위치를 target으로 추가하기 위한 설정
// 해당 설정을 통해서 소스 내에서 stub class를 참조 가능하다
sourceSets {
    main {
        java.srcDir("build/generated/source/main/grpckt")
        java.srcDir("build/generated/source/main/grpc")
        java.srcDir("build/generated/source/main/java")
    }
}

protobuf {
    generatedFilesBaseDir = "$projectDir/build/generated/source"
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.generateDescriptorSet = true
            it.descriptorSetOptions.includeSourceInfo = true
            it.descriptorSetOptions.includeImports = true
            it.descriptorSetOptions.path = "$buildDir/resources/META-INF/armeria/grpc/service-name.dsc"
        }
    }
}