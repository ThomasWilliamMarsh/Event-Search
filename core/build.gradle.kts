plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.minkSdk)
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "TICKETMASTER_API_KEY", "\"XpumtmXUMaZMTgnVA2UGNQ88okEFHMOk\"")

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
}

dependencies {

    //Android
    api("androidx.appcompat:appcompat:${Versions.appCompat}")
    api("androidx.fragment:fragment-ktx:${Versions.fragment}")
    api("androidx.activity:activity-ktx:${Versions.activity}")
    api("androidx.paging:paging-runtime:${Versions.paging}")
    api("androidx.room:room-runtime:${Versions.room}")
    api("androidx.room:room-ktx:${Versions.room}")
    api("androidx.datastore:datastore-preferences:${Versions.datastore}")
    api("com.google.android.material:material:${Versions.material}")
    api("androidx.startup:startup-runtime:${Versions.appStartup}")

    //Image loading
    api("io.coil-kt:coil:1.1.1")

    //Kotlin
    api("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    api("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    //Data
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")

    //Dagger
    api("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltJetpack}")
    kapt("androidx.room:room-compiler:${Versions.room}")

    //Time
    api("joda-time:joda-time:${Versions.jodaTime}")

    //Compose
    api("com.google.accompanist:accompanist-insets:${Versions.accompanist}")
    api("com.google.accompanist:accompanist-coil:${Versions.accompanist}")
    api("androidx.activity:activity-compose:${Versions.activity}")
    api("androidx.compose.runtime:runtime:${Versions.compose}")
    api("androidx.compose.animation:animation:${Versions.compose}")
    api("androidx.compose.animation:animation-core:${Versions.compose}")
    api("androidx.compose.ui:ui:${Versions.compose}")
    api("androidx.compose.foundation:foundation:${Versions.compose}")
    api("androidx.paging:paging-compose:${Versions.pagingCompose}")
    api("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    api("androidx.compose.material:material:${Versions.compose}")
    api("androidx.compose.material:material-icons-core:${Versions.compose}")
    api("androidx.compose.material:material-icons-extended:${Versions.compose}")
    api("androidx.compose.ui:ui-text:${Versions.compose}")
    api("androidx.compose.ui:ui-util:${Versions.compose}")
    api("androidx.compose.ui:ui-tooling:${Versions.compose}")
    api("androidx.navigation:navigation-compose:${Versions.composeNavigation}")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}")
    api("androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}")
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