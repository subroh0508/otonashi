plugins {
    id("com.android.application")
    id("kotlin-android")
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
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.ktx)
    implementation(Dep.AndroidX.constraintLayout)
    testImplementation(Dep.junit)
    androidTestImplementation(Dep.AndroidX.Test.runner)
    androidTestImplementation(Dep.AndroidX.Test.espressoCore)
}
