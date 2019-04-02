import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("jvm")
    maven
    `maven-publish`
}

group = "net.subroh0508.otonashi.vocabularies"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("generateVocabulary") {
            id = "net.subroh0508.otonashi-vocabularies-generator"
            version = Packages.Vocabularies.Generator.versionName
            implementationClass = "net.subroh0508.otonashi.vocabulary.generator.OtonashiVocabularyGeneratorPlugin"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compile(Dep.Kotlin.stdlibJdk8)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi-VocabularyGenerator", Packages.Vocabularies.Generator.versionName)
