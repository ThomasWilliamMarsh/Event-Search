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
        buildConfigField("String", "TICKETMASTER_API_KEY", "\"XpumtmXUMaZMTgnVA2UGNQ88okEFHMOk\"")

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

    //Android
    api(Deps.Android.appCompat)
    api(Deps.Android.fragment)
    api(Deps.Android.activity)
    api(Deps.Android.paging)
    api(Deps.Android.roomRuntime)
    api(Deps.Android.room)
    api(Deps.Android.datastore)
    api(Deps.Android.material)
    api(Deps.Android.startup)
    api(Deps.Android.coreKtx)
    api(Deps.Android.viewModel)
    kapt(Deps.Android.roomCompiler)

    //Image loading
    api(Deps.Util.coil)

    //Kotlin
    api(Deps.Kotlin.stdlib)
    api(Deps.Kotlin.refelct)
    api(Deps.Kotlin.coroutines)

    //Data
    api(Deps.Network.moshi)
    api(Deps.Network.moshiAdapter)
    api(Deps.Network.retrofit)

    //DI
    api(Deps.DI.hiltAndroid)
    kapt(Deps.DI.hiltAndroidCompiler)
    kapt(Deps.DI.hiltCompiler)

    //Time
    api(Deps.Util.jodaTime)

    //Compose
    api(Deps.Compose.insets)
    api(Deps.Compose.coil)
    api(Deps.Compose.activity)
    api(Deps.Compose.runtime)
    api(Deps.Compose.animation)
    api(Deps.Compose.animationCore)
    api(Deps.Compose.ui)
    api(Deps.Compose.foundation)
    api(Deps.Compose.foundationLayout)
    api(Deps.Compose.paging)
    api(Deps.Compose.material)
    api(Deps.Compose.materialIcons)
    api(Deps.Compose.materialIconsExt)
    api(Deps.Compose.text)
    api(Deps.Compose.uiUtil)
    api(Deps.Compose.uiTooling)
    api(Deps.Compose.navigation)
    api(Deps.Compose.viewModel)
    api(Deps.Compose.navigationHilt)
}