import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

// Load secrets from properties file
val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretsProperties = Properties().apply {
    if (secretsPropertiesFile.exists()) {
        secretsPropertiesFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.czar.transmiclock"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.czar.transmiclock"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // BuildConfig fields from secrets.properties
        buildConfigField("String", "APPID", "\"${secretsProperties["APPID"]}\"")
        buildConfigField("String", "URL_PARADEROS", "\"${secretsProperties["URL_PARADEROS"]}\"")
        buildConfigField("String", "URL_BUSES_PARADERO", "\"${secretsProperties["URL_BUSES_PARADERO"]}\"")
        buildConfigField("String", "URL_LOCALIZACION_BUSES", "\"${secretsProperties["URL_LOCALIZACION_BUSES"]}\"")
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    useLibrary("wear-sdk")
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.logging.interceptor)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui.tooling)
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.material3)
    implementation(libs.play.services.wearable)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.wear.tooling.preview)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)
}
