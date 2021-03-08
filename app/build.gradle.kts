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

    packagingOptions {
        excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
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
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("com.google.android.material:material:${Versions.material}")
    kapt("androidx.room:room-compiler:${Versions.room}")

    //Data
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")

    //Time
    implementation("joda-time:joda-time:${Versions.jodaTime}")

    //Compose
    implementation("dev.chrisbanes.accompanist:accompanist-insets:${Versions.accompanist}")
    implementation("dev.chrisbanes.accompanist:accompanist-coil:${Versions.accompanist}")
    implementation("androidx.activity:activity-compose:${Versions.activity}")
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation("androidx.compose.animation:animation:${Versions.compose}")
    implementation("androidx.compose.animation:animation-core:${Versions.compose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.paging:paging-compose:${Versions.pagingCompose}")
    implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    implementation("androidx.compose.material:material:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-core:${Versions.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
    implementation("androidx.compose.ui:ui-text:${Versions.compose}")
    implementation("androidx.compose.ui:ui-util:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("androidx.navigation:navigation-compose:${Versions.composeNavigation}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}")

    //Test
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")

    //DI
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpack}")
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    implementation("androidx.hilt:hilt-navigation:${Versions.hiltJetpack}")
    implementation("androidx.hilt:hilt-navigation-fragment:${Versions.hiltJetpack}")
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