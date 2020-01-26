plugins {
    kotlin("jvm").version("1.3.61")
}

group = "org.humanizer.jvm"
description = "TODO" // TODO
version = "1.0.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    testImplementation(group = "io.kotlintest", name = "kotlintest-runner-junit5", version = "3.1.11")
    testImplementation(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta2")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}