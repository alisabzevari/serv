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
    implementation(project(":serv-core"))
}
