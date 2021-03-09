plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
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

    //Kotlin
    api("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    api("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    //Data
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")

    //Dagger
    api("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpack}")
    api("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltJetpack}")
    kapt("androidx.room:room-compiler:${Versions.room}")

    //Time
    api("joda-time:joda-time:${Versions.jodaTime}")
}