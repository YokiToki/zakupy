plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("dev.icerock.mobile.multiplatform-units")
}
group = "com.github.yokitoki.zakupy"
version = "1.0"


dependencies {
    implementation(project(":mpp"))
    implementation("com.google.android.material:material:${rootProject.extra["android.material.version"]}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra["androidx.appcompat.version"]}")
//    implementation("androidx.lifecycle:lifecycle-extensions:${rootProject.extra["androidx.lifecycle.version"]}")
    implementation("androidx.recyclerview:recyclerview:${rootProject.extra["androidx.recyclerview.version"]}")
    implementation("androidx.constraintlayout:constraintlayout:${rootProject.extra["androidx.constraintlayout.version"]}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${rootProject.extra["androidx.swiperefreshlayout.version"]}")
    implementation("com.github.bumptech.glide:glide:${rootProject.extra["glide.version"]}")
}
android {
    compileSdkVersion(rootProject.extra["android.sdk.version"] as Int)
    buildFeatures.dataBinding = true
    dexOptions {
        javaMaxHeapSize = "2g"
    }
    defaultConfig {
        applicationId = "com.github.yokitoki.zakupy.android"
        minSdkVersion(rootProject.extra["android.sdk.min.version"] as Int)
        targetSdkVersion(rootProject.extra["android.sdk.target.version"] as Int)
        versionCode = rootProject.extra["app.version.code"] as Int
        versionName = rootProject.extra["app.version"] as String
        vectorDrawables.useSupportLibrary = true
        buildConfigField("String", "BASE_URL", "\"https://zakupyapp.herokuapp.com/\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}
multiplatformUnits {
    classesPackage = "com.github.yokitoki.zakupy.android"
    dataBindingPackage = "com.github.yokitoki.zakupy.android"
    layoutsSourceSet = "main"
}
