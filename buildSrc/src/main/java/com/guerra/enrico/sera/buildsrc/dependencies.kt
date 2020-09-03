@file:Suppress("unused")

package com.guerra.enrico.sera.buildsrc

object Versions {
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
    const val buildTools = "29.0.3"
}

object Libs {

    object Kotlin {
        const val version = "1.4.0"
        private const val coroutineVersion = "1.3.9"

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"

        object Test {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
            const val kotlin = "org.jetbrains.kotlin:kotlin-test:$version"
        }
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.3.1"

        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"

        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0-alpha07"
        const val activityKtx = "androidx.activity:activity-ktx:1.2.0-alpha07"

        const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"


        object Test {
            const val core = "androidx.test:core:1.2.0"
            const val coreKtx = "androidx.test:core-ktx:1.2.0"
            const val archCore = "androidx.arch.core:core-testing:2.1.0"
            const val junitKtx = "androidx.test.ext:junit-ktx:1.1.2-rc03"
            const val runner = "androidx.test:runner:1.3.0-rc03"
            const val rules = "androidx.test:rules:1.3.0-rc03"
        }
    }

    object Lifecycle {
        private const val version = "2.2.0"

        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.0-beta01"
        const val flexbox = "com.google.android:flexbox:2.0.1"
    }

    object Hilt {
        const val androidVersion = "2.28.3-alpha"
        private const val version = "1.0.0-alpha02"

        const val android = "com.google.dagger:hilt-android:$androidVersion"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:$androidVersion"

        const val lifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
        const val compiler = "androidx.hilt:hilt-compiler:$version"
        const val work = "androidx.hilt:hilt-work:$version"

        object Test {
            const val android = "com.google.dagger:hilt-android-testing:$androidVersion"
            const val androidCompiler = "com.google.dagger:hilt-android-compiler:$androidVersion"
        }
    }

    object Retrofit {
        private const val version = "2.8.1"

        const val lib = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Room {
        private const val version = "2.2.5"

        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val ktx = "androidx.room:room-ktx:$version"
    }

    const val Gson = "com.google.code.gson:gson:2.8.6"

    const val WorkManager = "androidx.work:work-runtime-ktx:2.4.0"

    const val Timber = "com.jakewharton.timber:timber:4.7.1"

    object Navigation {
        const val version = "2.3.0"

        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object PlayServices {
        private const val version = "18.1.0"

        const val auth = "com.google.android.gms:play-services-auth:$version"
    }

    object Lint {
        private const val version = "26.4.1"

        const val api = "com.android.tools.lint:lint-api:$version"
        const val checks = "com.android.tools.lint:lint-checks:$version"
    }

    // Test

    const val Junit = "junit:junit:4.13"

    const val Robolectric = "org.robolectric:robolectric:4.3.1"

    const val MockitoCore = "org.mockito:mockito-core:2.23.4"

    object Mockk {
        private const val version = "1.10.0"

        const val lib = "io.mockk:mockk:$version"
        const val android = "io.mockk:mockk-android:$version"
    }

    object Espresso {
        private const val version = "3.3.0-rc03"

        const val core = "androidx.test.espresso:espresso-core:$version"
        const val contrib = "androidx.test.espresso:espresso-contrib:$version"
        const val idlingResource = "androidx.test.espresso:espresso-idling-resource:$version"
        const val intents = "androidx.test.espresso:espresso-intents:$version"
    }

}