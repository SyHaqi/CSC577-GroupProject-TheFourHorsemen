plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.haqi.csc577groupproject"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.haqi.csc577groupproject"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit — REST API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter — JSON to Java objects
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson — needed by LoginActivity for FailLogin parsing
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.viewpager2:viewpager2:1.1.0")
}