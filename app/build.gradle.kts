plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.englishteacher"
    compileSdk = compileAndroidSdk

    defaultConfig {
        applicationId = "com.example.englishteacher"
        minSdk = minAndroidSdk
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
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation(Libs.Android.CoreKtx)
    implementation(Libs.Android.AppCompat)
    implementation(Libs.Android.ConstraintLayout)

    implementation(Libs.Google.Material)
    implementation(Libs.Google.Paging)

    implementation(Libs.Google.Hilt)
    kapt(Libs.Google.HiltCompiler)

    implementation(project(Mod.Data))
    implementation(project(Mod.Core.Presentation))
    implementation(project(Mod.Features.Catalog))
    implementation(project(Mod.Features.Game))
    implementation(project(Mod.Features.Profile))
    implementation(project(Mod.Features.SignIn))
    implementation(project(Mod.Features.SignUp))
    implementation(project(Mod.Features.Redactor))

}