plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Release signing comes from environment variables (set from GitHub secrets in CI,
// or locally if you export them). Without them the build falls back to the debug key,
// which installs fine but will NOT update over a properly signed install.
val signingStoreFile: String? = System.getenv("LOMOCAM_KEYSTORE_FILE")
val hasReleaseSigning = !signingStoreFile.isNullOrBlank() && file(signingStoreFile).exists()

// Every CI run gets a higher versionCode so Android always accepts it as an update.
val ciRunNumber = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 1

android {
    namespace = "com.kingof9x.lomocam"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.kingof9x.lomocam"
        // Single target device: Pixel 10 Pro on the latest stable Android.
        minSdk = 36
        targetSdk = 37
        versionCode = ciRunNumber
        versionName = "0.1.$ciRunNumber"
    }

    signingConfigs {
        create("release") {
            if (hasReleaseSigning) {
                storeFile = file(signingStoreFile!!)
                storePassword = System.getenv("LOMOCAM_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("LOMOCAM_KEY_ALIAS")
                keyPassword = System.getenv("LOMOCAM_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = if (hasReleaseSigning) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
}
