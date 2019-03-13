plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "net.subroh0508.sparkl.sampleapp"
        minSdkVersion(22)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
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
    val kotlinVersion: String by project

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.appcompat:appcompat:1.0.0-beta01")
    implementation("androidx.core:core-ktx:1.1.0-alpha04")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.0-alpha4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0-alpha4")
}
