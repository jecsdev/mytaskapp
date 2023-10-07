import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id ("kotlin-kapt")

}

android {
    namespace = "com.jecsdev.mytasklist"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.jecsdev.mytasklist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner= "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {
    val mockkVersion = "1.13.7"
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.ui:ui:1.5.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation ("androidx.compose.material:material:1.5.3")

    //Test dependencies
    testImplementation ("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:${mockkVersion}")

    androidTestImplementation("io.mockk:mockk-android:${mockkVersion}")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.3")

    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.3")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.3")

    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.navigation:navigation-compose:2.7.3")
    implementation ("androidx.compose.material:material-icons-extended:1.5.3")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.43.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.43.2")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    // Room
    implementation ("androidx.room:room-runtime:2.5.2")
    ksp ("androidx.room:room-compiler:2.5.2")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.5.2")
}