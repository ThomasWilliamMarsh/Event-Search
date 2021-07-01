plugins {
    id(Deps.Plugin.application)
    id(Deps.Plugin.hilt)
    id(Deps.Plugin.kotlin)
    id(Deps.Plugin.kapt)
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    defaultConfig {
        applicationId = Application.id
        minSdk = Versions.minkSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = Application.testRunner
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion =  Versions.compose
    }

}

dependencies {

    implementation(project(Deps.Modules.core))
    implementation(project(Deps.Modules.settings))
    implementation(project(Deps.Modules.attraction))
    implementation(project(Deps.Modules.category))

    implementation(Deps.DI.hiltAndroid)

    kapt(Deps.Android.roomCompiler)
    kapt(Deps.DI.hiltAndroidCompiler)
    kapt(Deps.DI.hiltCompiler)
}