plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.projectsubmissionandroidfundamentalbangkit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projectsubmissionandroidfundamentalbangkit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
//      Change with your API Key
//      buildConfigField("String", "API_KEY", "\"\"")

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
    buildFeatures {
        viewBinding=true
        buildConfig=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Material Theme
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Fragment Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // GSON converter for serialization
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Retrofit2 dependency
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Coroutine dependency for Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle extension for ViewModel with Kotlin coroutines support
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Room components
    implementation("androidx.room:room-runtime:2.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.mockito:mockito-inline:2.8.47")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}