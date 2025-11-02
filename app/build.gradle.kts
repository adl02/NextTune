plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.howtokaise.nexttune"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.howtokaise.nexttune"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    // navigation
    implementation(libs.androidx.navigation.compose)
    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    // For WebSocket
    implementation("io.socket:socket.io-client:2.0.1") { exclude (group = "org.json", module = "json") }
    // For JSON
    implementation(libs.gson)
    // viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    // gson
    implementation("com.google.code.gson:gson:2.10.1")
// ViewModel & LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.9.4")
    // coil
    implementation ("io.coil-kt:coil-compose:2.7.0")
    //icon
    implementation("androidx.compose.material:material-icons-extended")
    // pager
    implementation("androidx.compose.foundation:foundation:1.9.4")
}