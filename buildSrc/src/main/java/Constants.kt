val hiltVersion = "2.44"
val roomVersion = "2.6.1"
val pagingVersion = "3.2.1"
val retrofitVersion = "2.9.0"
val navVersion = "2.7.6"

val compileAndroidSdk = 34
val minAndroidSdk = 24
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
        val NavigationFragment = "androidx.navigation:navigation-fragment:$navVersion"
        val NavigationUI = "androidx.navigation:navigation-ui:$navVersion"
    }
    object Google{
        val Material = "com.google.android.material:material:1.11.0"

        val Hilt = "com.google.dagger:hilt-android:$hiltVersion"
        val HiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"

        val RoomKtx = "androidx.room:room-ktx:$roomVersion" //for Kotlin Flows, Coroutines
        val RoomCompiler = "androidx.room:room-compiler:$roomVersion"
        val RoomRuntime = "androidx.room:room-runtime:$roomVersion"
        val RoomPaging = "androidx.room:room-paging:$roomVersion"

        val Paging = "androidx.paging:paging-runtime:$pagingVersion"
    }
    object SquareUp {
        val Moshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
        val Retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    }
    object JetBrains  {
        val CoroutineScope = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    }
    object Testing  {
        val Junit = "junit:junit:4.13.2"
    }
}

object Mod {
    val Data = ":data"
    object Features {
        val SignUp = ":features:sign-up"
        val SignIn = ":features:sign-in"
        val Catalog = ":features:catalog"
        val Favorites = ":features:favorites"
        val Game = ":features:game"
        val Peoples = ":features:peoples"
        val Profile = ":features:profile"
    }
    object Core {
        val Presentation = ":core:presentation"
        val Common = ":core:common"
    }
}
