import Compose.ComposeDependencies

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
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
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion =  "1.4.30"
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
    implementation("androidx.activity:activity-compose:${Versions.activity}")
    implementation("androidx.activity:activity-ktx:${Versions.activity}")
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("com.google.android.material:material:${Versions.material}")
    implementation("androidx.paging:paging-compose:${Versions.pagingCompose}")
    implementation("androidx.hilt:hilt-navigation:${Versions.hiltJetpack}")
    implementation("androidx.hilt:hilt-navigation-fragment:${Versions.hiltJetpack}")
    kapt("androidx.room:room-compiler:${Versions.room}")

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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        // Treat all Kotlin warnings as errors
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=androidx.compose.foundation.lazy.ExperimentalLazyDsl",
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check",
            "-Xopt-in=kotlin.Experimental"
        )
        // Set JVM target to 1.8
        jvmTarget = Versions.java
        useIR = true
    }
}