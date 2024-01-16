val hiltVersion = "2.44"
val compileAndroidSdk = 34
val minAndroidSkd = 24
val targetAndroidSdk = 33

object Libs {
    object Android {
        val CoreKtx = "androidx.core:core-ktx:1.12.0"
        val FragmentKtx = "androidx.fragment:fragment-ktx:1.6.2"
        val ActivityKtx = "androidx.activity:activity-ktx:1.8.2"
        val AppCompat = "androidx.appcompat:appcompat:1.6.1"
        val LifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
        val LifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
        val ConstraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        val RecyclerView = "androidx.recyclerview:recyclerview:1.3.2"
    }
    object Google{
        val Material = "com.google.android.material:material:1.11.0"
        val Hilt = "com.google.dagger:hilt-android:$hiltVersion"
        val HiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    }
    object JetBrains  {
        val CoroutineScope = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    }
    object Testing  {
        val Junit = "junit:junit:4.13.2"
    }
}

val Modules = object {
    val Data = ":data"
    val Features = object {
        val SignUp = ":features:sign-up"
        val SignIn = ":features:sign-in"
        val Catalog = ":features:catalog"
        val Favorites = ":features:favorites"
        val Game = ":features:game"
        val Peoples = ":features:peoples"
        val Profile = ":features:profile"
    }
}
