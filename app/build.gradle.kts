plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.englishteacher"
    compileSdk = compileAndroidSdk

    defaultConfig {
        applicationId = "com.example.englishteacher"
        minSdk = minAndroidSkd
        targetSdk = targetAndroidSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kapt {
        correctErrorTypes = true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(Libs.Android.CoreKtx)
    implementation(Libs.Android.AppCompat)
    implementation(Libs.Android.ConstraintLayout)

    implementation(Libs.Google.Material)

    implementation(Libs.Google.Hilt)
    kapt(Libs.Google.HiltCompiler)

}