plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
    compileSdk = compileAndroidSdk

    defaultConfig {
        minSdk = minAndroidSkd

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
}

dependencies {

    implementation(Libs.Android.CoreKtx)

    implementation(Libs.SquareUp.Retrofit)
    implementation(Libs.SquareUp.Moshi)

    implementation(Libs.Google.RoomKtx)
    implementation(Libs.Google.RoomCompiler)
    implementation(Libs.Google.RoomRuntime)
    implementation(Libs.Google.RoomPaging)

    implementation(Libs.Google.Paging)

    implementation(Libs.Google.Hilt)
    kapt(Libs.Google.HiltCompiler)

}