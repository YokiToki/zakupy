buildscript {
    extra.apply{
        set("app.version", "0.1.0")
        set("app.version.code", 1)
        set("kotlin.version", "1.4.10")
        set("kotlin.coroutines.version", "1.3.9-native-mt")
        set("kotlinx.serialization.version", "1.0.0-RC")
        set("ktor.client.version", "1.4.0")
        set("icerock.multiplatform.version", "0.8.0")
        set("moko.mvvm.version", "0.8.0")
        set("moko.network.version", "0.8.0")
        set("moko.units.version", "0.4.1")
        set("moko.resources.version", "0.13.1")
        set("moko.parcelize.version", "0.4.0")
        set("moko.graphics.version", "0.4.0")
        set("moko.errors.version", "0.3.0")
        set("moko.permissions.version", "0.6.0")
        set("moko.media.version", "0.5.0")
        set("moko.fields.version", "0.5.0")
        set("android.sdk.version", 29)
        set("android.sdk.min.version", 24)
        set("android.sdk.target.version", 29)
        set("android.material.version", "1.2.1")
        set("android.tools.build.version", "4.0.1")
        set("androidx.appcompat.version", "1.2.0")
        set("androidx.lifecycle.version", "2.2.0")
        set("androidx.recyclerview.version", "1.1.0")
        set("androidx.constraintlayout.version", "2.0.4")
        set("androidx.swiperefreshlayout.version", "1.1.0")
        set("napier.version", "1.4.1")
        set("multiplatform-settings.version", "0.6.1")
        set("glide.version", "4.9.0")
    }
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven { setUrl("https://dl.bintray.com/icerockdev/plugins") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${project.extra["android.tools.build.version"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin.version"]}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${project.extra["kotlin.version"]}")
        classpath("dev.icerock:mobile-multiplatform:${project.extra["icerock.multiplatform.version"]}")
        classpath("dev.icerock.moko:network-generator:${project.extra["moko.network.version"]}")
        classpath("dev.icerock.moko:resources-generator:${project.extra["moko.resources.version"]}")
        classpath("dev.icerock.moko:units-generator:${project.extra["moko.units.version"]}")
    }
}

group = "com.github.yokitoki.zakupy"
version = project.extra["app.version"] as String

allprojects {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven { setUrl("https://kotlin.bintray.com/kotlin") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://dl.bintray.com/icerockdev/moko") }
        maven { setUrl("https://kotlin.bintray.com/ktor") }
        maven { setUrl("https://dl.bintray.com/aakira/maven") }
        maven { setUrl("https://dl.bintray.com/lukaville/maven") }
    }
//    plugins.withId("com.android.library") {
//        configure<com.android.build.gradle.LibraryExtension> {
//            compileSdkVersion(project.extra["android.sdk.version"] as Int)
//
//            defaultConfig {
//                minSdkVersion(project.extra["android.sdk.min.version"] as Int)
//                targetSdkVersion(project.extra["android.sdk.target.version"] as Int)
//            }
//        }
//    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
