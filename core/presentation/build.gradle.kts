plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.presentation"
    compileSdk = compileAndroidSdk

    defaultConfig {
        minSdk = minAndroidSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Libs.Android.CoreKtx)
    implementation(Libs.Android.AppCompat)
    implementation(Libs.Android.LifecycleRuntimeKtx)
    implementation(Libs.Android.FragmentKtx)

    implementation(Libs.Google.Paging)
    implementation(Libs.Google.Material)

    api(Libs.Android.NavigationFragment)
    api(Libs.Android.NavigationUI)

    api(project(Mod.Core.Common))
}