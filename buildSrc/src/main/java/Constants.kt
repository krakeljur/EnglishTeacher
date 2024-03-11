const val hiltVersion = "2.48"
const val roomVersion = "2.6.1"
const val pagingVersion = "3.2.1"
const val retrofitVersion = "2.9.0"
const val navVersion = "2.7.6"

const val compileAndroidSdk = 34
const val minAndroidSdk = 24
const val targetAndroidSdk = 33

object Libs {
    object Android {
        const val CoreKtx = "androidx.core:core-ktx:1.12.0"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:1.6.2"
        const val ActivityKtx = "androidx.activity:activity-ktx:1.8.2"
        const val AppCompat = "androidx.appcompat:appcompat:1.6.1"
        const val LifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
        const val LifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
        const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val RecyclerView = "androidx.recyclerview:recyclerview:1.3.2"
        const val NavigationFragment = "androidx.navigation:navigation-fragment:$navVersion"
        const val NavigationUI = "androidx.navigation:navigation-ui:$navVersion"
    }
    object Google{
        const val Material = "com.google.android.material:material:1.11.0"

        const val Hilt = "com.google.dagger:hilt-android:$hiltVersion"
        const val HiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"

        const val RoomKtx = "androidx.room:room-ktx:$roomVersion" //for Kotlin Flows, Coroutines
        const val RoomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val RoomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val RoomPaging = "androidx.room:room-paging:$roomVersion"

        const val Paging = "androidx.paging:paging-runtime:$pagingVersion"
    }
    object SquareUp {
        const val Moshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
        const val Retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    }
    object JetBrains  {
        const val CoroutineScope = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    }
    object Testing  {
        const val Junit = "junit:junit:4.13.2"
    }
}

object Mod {
    const val Data = ":data"
    object Features {
        const val SignUp = ":features:sign-up"
        const val SignIn = ":features:sign-in"
        const val Catalog = ":features:catalog"
        const val Game = ":features:game"
        const val Peoples = ":features:peoples"
        const val Profile = ":features:profile"
    }
    object Core {
        const val Presentation = ":core:presentation"
        const val Common = ":core:common"
    }
}
