plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":data"))

    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntimeKtx)

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(platform(Libs.AndroidX.Compose.composeBom))
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiGraphics)
    implementation(Libs.AndroidX.Compose.uiToolingPreview)
    implementation(Libs.AndroidX.Compose.material3)

    implementation (Libs.AndroidX.Navigation.navigationCompose)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(platform(Libs.AndroidX.Compose.composeBom))
    androidTestImplementation(Libs.AndroidX.Compose.uiTestJunit4)

    debugImplementation(Libs.AndroidX.Compose.uiTooling)
    debugImplementation(Libs.AndroidX.Compose.uiTestManifest)
}