import org.gradle.configurationcache.extensions.capitalized
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

apply<AddCurrentDatePlugin>()

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
        debug {
        }
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

abstract class AddCurrentDateTask: DefaultTask() {
    @get:Input
    abstract val buildFlavor: Property<String>

    @get:Input
    abstract val buildType: Property<String>

    @TaskAction
    fun taskAction() {
        val oldFileName = "app-${buildFlavor.get()}-${buildType.get()}.apk"
        val sourcePath = "${project.buildDir}/outputs/apk/${buildFlavor.get()}/${buildType.get()}/"
        val date = SimpleDateFormat("dd-MM-yyyy").format(Date())
        project.copy {
            from(sourcePath + oldFileName)
            into(sourcePath)
            rename { date + "_" + oldFileName  }
        }
    }
}

class AddCurrentDatePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.androidComponents {
            onVariants(selector().all()) { variant ->
                val addCurrentDateTask = target.tasks.register(
                    name = variant.name + "AddCurrentDateTask",
                    type = AddCurrentDateTask::class
                ) {
                    buildFlavor.set(variant.flavorName.orEmpty())
                    buildType.set(variant.buildType)
                }

                val assembleTaskName = "assemble${variant.name.capitalized()}"

                target.tasks.matching { it.name == assembleTaskName }.configureEach {
                    finalizedBy(addCurrentDateTask)
                }
            }
        }
    }
}

//androidComponents {
//    onVariants(selector().all()) { variant ->
//        val addCurrentDateTask = project.tasks.register(
//            name = variant.name + "AddCurrentDateTask",
//            type = AddCurrentDateTask::class
//        ) {
//            buildFlavor.set(variant.flavorName.orEmpty())
//            buildType.set(variant.buildType)
//        }
//        val assembleTaskName = "assemble${variant.name.capitalized()}"
//        project.tasks.matching { it.name == assembleTaskName }.configureEach {
//            finalizedBy(addCurrentDateTask)
//        }
//    }
//}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.picasso)
}
