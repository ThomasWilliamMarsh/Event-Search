object Deps {

    object Plugin {
        const val application = "com.android.application"
        const val hilt = "dagger.hilt.android.plugin"
        const val kotlin = "kotlin-android"
        const val library = "com.android.library"
        const val kapt = "org.jetbrains.kotlin.kapt"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val refelct = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Android {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
        const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val room = "androidx.room:room-ktx:${Versions.room}"
        const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val startup = "androidx.startup:startup-runtime:${Versions.appStartup}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    }

    object Compose {
        const val insets = "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
        const val coil = "com.google.accompanist:accompanist-coil:${Versions.accompanist}"
        const val activity = "androidx.activity:activity-compose:${Versions.activity}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val animation = "androidx.compose.animation:animation:${Versions.compose}"
        const val animationCore =
            "androidx.compose.animation:animation-core:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
        const val foundationLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIcons =
            "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val materialIconsExt =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val text = "androidx.compose.ui:ui-text:${Versions.compose}"
        const val uiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val navigation =
            "androidx.navigation:navigation-compose:${Versions.navigation}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val navigationHilt =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
    }

    object DI {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.dagger}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetpack}"
    }

    object Network {
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val moshiAdapter =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    }

    object Util {
        const val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"
        const val coil= "io.coil-kt:coil:${Versions.coil}"
    }

    object Modules {
        const val core = ":core"
        const val attraction = ":attraction"
        const val category = ":category"
        const val settings = ":settings"
    }
}