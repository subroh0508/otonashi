@Suppress("unused")
object Dep {
    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"

        const val jfrog = "com.jfrog.bintray"
    }

    object Kotlin {
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val ktx = "androidx.core:core-ktx:${Versions.AndroidX.ktx}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"

        object Test {
            const val runner = "androidx.test:runner:${Versions.AndroidX.Test.runner}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.AndroidX.Test.espressoCore}"
        }
    }

    const val material = "com.google.android.material:material:${Versions.material}"

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Ktor {
        const val clientCommon = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val clientApache = "io.ktor:ktor-client-apache:${Versions.ktor}"
        const val jsonCommon = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val jsonJvm = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
    }

    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"

    const val junit = "junit:junit:${Versions.junit}"
}