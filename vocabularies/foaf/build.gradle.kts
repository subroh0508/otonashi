import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("net.subroh0508.otonashi-vocabularies-generator") version Packages.Vocabularies.generatorVersion
}

group = "net.subroh0508.otonashi.vocabularies"
version = "1.0-SNAPSHOT"

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
    endpoint = "https://gist.githubusercontent.com/baskaufs/fefa1bfbff14a9efc174/raw/389e4b003ef5cbd6901dd8ab8a692b501bc9370e/foaf.ttl"
    outputPath = "src/main/kotlin/net/subroh0508/otonashi/vocabularies/foaf"
    prefix(
        "foaf" to "<http://xmlns.com/foaf/0.1/>"
    )
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi-foaf", Packages.Vocabularies.foafVersion)