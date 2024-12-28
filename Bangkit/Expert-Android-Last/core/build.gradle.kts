import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

val apiKey : String? = System.getenv("API_KEY") ?: run {
    val apiKeyPropertiesFile = rootProject.file("apiKey.properties")
    val apiKeyProperties = Properties()

    if (apiKeyPropertiesFile.exists()) {
        apiKeyProperties.load(apiKeyPropertiesFile.inputStream())
        apiKeyProperties.getProperty("API_KEY")
    } else null
}

android {
    namespace = "com.example.flickscout.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "API_KEY", "\"${apiKey}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)

    // Fragment Navigation
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.fragment.ktx)

    // GSON converter for serialization
    implementation(libs.converter.gson)

    // Retrofit2 dependency
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Coroutine dependency for Android
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle extension for ViewModel with Kotlin coroutines support
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // RxJava
    implementation(libs.rxjava)

    // Koin
    api(libs.koin.android)

    // Coil
    api(libs.coil)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.legacy.support.v4)

    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
    debugImplementation(libs.leakcanary.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}