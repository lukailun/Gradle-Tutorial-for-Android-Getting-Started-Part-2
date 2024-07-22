@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kodeco.socializify"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kodeco.socializify"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    signingConfigs {
        create("release") {
            storeFile = file("path to your keystore file")
            storePassword = "your store password"
            keyAlias = "your key alias"
            keyPassword = "your key password"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
        debug { }
    }

    flavorDimensions.add("appMode")
    productFlavors {
        create("free") {
            dimension = "appMode"
            applicationIdSuffix = ".free"
            manifestPlaceholders["appName"] = "@string/app_name_free"
        }
        create("paid") {
            dimension = "appMode"
            applicationIdSuffix = ".paid"
            manifestPlaceholders["appName"] = "@string/app_name_paid"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.picasso)
}
