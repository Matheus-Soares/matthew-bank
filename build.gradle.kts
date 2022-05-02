plugins {
    base
    java
    kotlin("jvm") version "1.6.10" apply false
    id("io.quarkus") apply false
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.quarkus")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
        implementation("io.quarkus:quarkus-resteasy-reactive")
        implementation("io.quarkus:quarkus-arc")
        implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
        implementation("io.quarkus:quarkus-jdbc-postgresql")
        implementation("io.quarkus:quarkus-smallrye-openapi")
//        implementation("io.quarkus:quarkus-smallrye-reactive-messaging-rabbitmq")
//        implementation("io.quarkus:quarkus-smallrye-reactive-messaging-amqp")
        implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
        testImplementation("io.quarkus:quarkus-panache-mock")
        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
    }

    group = "com.bank.matthew"
    version = "1.0-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
        kotlinOptions.javaParameters = true
    }
}
