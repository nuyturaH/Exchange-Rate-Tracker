plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.harutyun.exchangeratetracker"
    compileSdk = Versions.Build.compileSdk

    defaultConfig {
        applicationId = "com.harutyun.exchangeratetracker"
        minSdk = Versions.Build.minSdk
        targetSdk = Versions.Build.targetSdk
        versionCode = Versions.Build.versionCode
        versionName = Versions.Build.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Libs.AndroidX.coreKtx)


    // UI
    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(platform(Libs.AndroidX.Compose.composeBom))
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiGraphics)
    implementation(Libs.AndroidX.Compose.uiToolingPreview)
    implementation(Libs.AndroidX.Compose.material3)

    // Lifecycle
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntimeKtx)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntimeCompose)

    // Navigation
    implementation (Libs.AndroidX.Navigation.navigationCompose)

    // DI
    implementation(Libs.Dagger.hiltAndroid)
    ksp(Libs.Dagger.hiltAndroidCompiler)
    implementation (Libs.AndroidX.Hilt.navigationCompose)


    // Retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converterMoshi)

    // Room
    implementation(Libs.AndroidX.Room.roomKtx)

    // Test
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(platform(Libs.AndroidX.Compose.composeBom))
    androidTestImplementation(Libs.AndroidX.Compose.uiTestJunit4)

    // Debug
    debugImplementation(Libs.AndroidX.Compose.uiTooling)
    debugImplementation(Libs.AndroidX.Compose.uiTestManifest)
}