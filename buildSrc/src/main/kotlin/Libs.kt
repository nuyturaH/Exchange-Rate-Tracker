object Libs {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.12.0"

        object Lifecycle {
            private const val lifecycleVersion = "2.6.2"
            const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.7.2"
        }

        object Compose {
            const val composeBom = "androidx.compose:compose-bom:2023.09.02"
            const val ui = "androidx.compose.ui:ui"
            const val uiGraphics = "androidx.compose.ui:ui-graphics"
            const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val material3 = "androidx.compose.material3:material3"
            const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
            const val uiTooling = "androidx.compose.ui:ui-tooling"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
        }

        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.7.3"
        }

        object Test {
            const val junit = "androidx.test.ext:junit:1.1.5"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
        }
    }

    object Dagger {
        const val daggerVersion = "2.48"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerVersion"
    }

    object Retrofit {
        const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    }

    object Okhttp {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
    }
}
