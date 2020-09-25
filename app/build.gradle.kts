import Compose.ComposeDependencies

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        applicationId = Application.id
        minSdkVersion(Versions.minkSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Versions.java
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Versions.kotlin
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {

    implementation(project(":core"))

    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    //Android
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    implementation("androidx.compose.material:material:${Versions.compose}")
    implementation("androidx.ui:ui-tooling:${Versions.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")

    //Data
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")

    //Time
    implementation("joda-time:joda-time:${Versions.jodaTime}")

    //Compose
    ComposeDependencies.forEach { dependency ->
        implementation(dependency)
    }

    //Dagger
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpack}")
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltJetpack}")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        // Treat all Kotlin warnings as errors
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=androidx.compose.foundation.lazy.ExperimentalLazyDsl",
            "-Xopt-in=kotlin.Experimental",
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check"
        )
        // Set JVM target to 1.8
        jvmTarget = Versions.java
    }
}