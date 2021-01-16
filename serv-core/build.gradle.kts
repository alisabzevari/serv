plugins {
    kotlin("jvm") version "1.4.21"
}

group = "io.serv"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("io.kotest:kotest-runner-junit5:4.3.2")
    testImplementation("io.kotest:kotest-assertions-core:4.3.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
