plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlinx-serialization")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Packages.compileSdkVersion)
    defaultConfig {
        applicationId = Packages.applicationId
        minSdkVersion(Packages.minSdkVersion)
        targetSdkVersion(Packages.targetSdkVersion)
        versionCode = Packages.versionCode
        versionName = Packages.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets.forEach {
        it.java.setSrcDirs(files("src/${it.name}/kotlin"))
    }
    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(project(":core"))
    implementation(Dep.Kotlin.stdlibJdk7)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.ktx)
    implementation(Dep.AndroidX.constraintLayout)
    implementation(Dep.material)
    implementation(Dep.Coroutines.core)
    implementation(Dep.Coroutines.android)
    implementation(Dep.Ktor.clientCommon)
    implementation(Dep.Ktor.clientAndroid)
    implementation(Dep.Ktor.jsonCommon)
    implementation(Dep.Ktor.jsonJvm)
    testImplementation(Dep.junit)
    androidTestImplementation(Dep.AndroidX.Test.runner)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
}
