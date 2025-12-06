import java.io.FileInputStream
import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

val properties = Properties()
// Load the properties file from the root directory
rootProject.file("local.properties").takeIf { it.exists() }?.let {
    properties.load(FileInputStream(it))
}
android {
    namespace = "com.example.collegeeventmanager"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "CLOUDINARY_CLOUD_NAME",
            value = properties.getProperty("CLOUDINARY_CLOUD_NAME") ?: "\"MISSING_CLOUD_NAME\""
        )
        buildConfigField(
            type = "String",
            name = "CLOUDINARY_API_KEY",
            value = properties.getProperty("CLOUDINARY_API_KEY") ?: "\"MISSING_KEY\""
        )
        buildConfigField(
            type = "String",
            name = "CLOUDINARY_API_SECRET",
            value = properties.getProperty("CLOUDINARY_API_SECRET") ?: "\"MISSING_SECRET\""
        )
        applicationId = "com.example.collegeeventmanager"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "3.1.2"

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
    buildFeatures{
        viewBinding=true
        buildConfig=true

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.inputmapping)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(libs.firebase.storage)
    implementation("com.cloudinary:cloudinary-android:3.1.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}