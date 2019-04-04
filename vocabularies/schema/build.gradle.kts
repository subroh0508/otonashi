import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("net.subroh0508.otonashi-vocabularies-generator") version Packages.Vocabularies.generatorVersion
}

group = "net.subroh0508.otonashi.vocabularies"
version = Packages.Vocabularies.schemaVersion

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compile(project(":triples"))
    compile(Dep.Kotlin.stdlibJdk8)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

generateVocabulary {
    endpoint = "http://schema.org/version/latest/schema.ttl"
    outputPath = "src/main/kotlin/net/subroh0508/otonashi/vocabularies/schema"
    prefix(
        "schema" to "<http://schema.org/>"
    )
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi-Schema", Packages.Vocabularies.schemaVersion)
