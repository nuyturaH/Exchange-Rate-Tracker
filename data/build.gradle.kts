plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.harutyun.data"
    compileSdk = Versions.Build.compileSdk

    defaultConfig {
        minSdk = Versions.Build.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://api.apilayer.com/exchangerates_data/\"")
        buildConfigField("String", "API_KEY", "\"bEBq0KcK9o9yTi0TnqeR8gPYbAQoVEWZ\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(Libs.AndroidX.coreKtx)

    // Retrofit
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.converterMoshi)

    // Okhttp
    implementation(Libs.Okhttp.loggingInterceptor)

    // Room
    annotationProcessor(Libs.AndroidX.Room.roomCompiler)
    implementation(Libs.AndroidX.Room.roomKtx)
    ksp(Libs.AndroidX.Room.roomCompiler)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junit)
}