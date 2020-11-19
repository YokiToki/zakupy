import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("dev.icerock.mobile.multiplatform")
    id("dev.icerock.mobile.multiplatform.ios-framework")
    id("dev.icerock.mobile.multiplatform-network-generator")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = "com.github.yokitoki.zakupy"
version = rootProject.extra["app.version"] as String

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "mpp"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("dev.icerock.moko:mvvm:${rootProject.extra["moko.mvvm.version"]}")
                api("dev.icerock.moko:network:${rootProject.extra["moko.network.version"]}")
                api("dev.icerock.moko:network-errors:${rootProject.extra["moko.network.version"]}")
                api("dev.icerock.moko:units:${rootProject.extra["moko.units.version"]}")
                api("dev.icerock.moko:resources:${rootProject.extra["moko.resources.version"]}")
                api("dev.icerock.moko:parcelize:${rootProject.extra["moko.parcelize.version"]}")
                api("dev.icerock.moko:graphics:${rootProject.extra["moko.graphics.version"]}")
                api("dev.icerock.moko:errors:${rootProject.extra["moko.errors.version"]}")
                api("dev.icerock.moko:permissions:${rootProject.extra["moko.permissions.version"]}")
                api("dev.icerock.moko:media:${rootProject.extra["moko.media.version"]}")
                api("dev.icerock.moko:fields:${rootProject.extra["moko.fields.version"]}")
                api("com.github.aakira:napier:${rootProject.extra["napier.version"]}")
                api("com.russhwolf:multiplatform-settings:${rootProject.extra["multiplatform-settings.version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${rootProject.extra["kotlinx.serialization.version"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["kotlin.coroutines.version"]}")
                implementation("io.ktor:ktor-client-core:${rootProject.extra["ktor.client.version"]}")
                implementation("io.ktor:ktor-client-logging:${rootProject.extra["ktor.client.version"]}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:${rootProject.extra["android.material.version"]}")
                implementation("androidx.lifecycle:lifecycle-extensions:${rootProject.extra["androidx.lifecycle.version"]}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}
android {
    compileSdkVersion(rootProject.extra["android.sdk.version"] as Int)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(rootProject.extra["android.sdk.min.version"] as Int)
        targetSdkVersion(rootProject.extra["android.sdk.target.version"] as Int)
        versionCode = rootProject.extra["app.version.code"] as Int
        versionName = rootProject.extra["app.version"] as String
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.github.yokitoki.zakupy.mpp"
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

openApiGenerate {
    inputSpec.set(file("../openapi/openapi.yaml").path)
    generatorName.set("kotlin-ktor-client")
}