plugins {
    id(Deps.Plugin.library)
    id(Deps.Plugin.kotlin)
    id(Deps.Plugin.kapt)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minkSdk
        testInstrumentationRunner = Application.testRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(Deps.Modules.core))

    implementation(Deps.DI.hiltAndroid)

    kapt(Deps.DI.hiltAndroidCompiler)
    kapt(Deps.DI.hiltCompiler)
}