import org.gradle.kotlin.dsl.*

plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
}