import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("kotlinx-serialization")
}

group = "net.subroh0508.otonashi"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    compile(project(":core"))
    compile(project(":vocabularies:foaf"))
    compile(project(":vocabularies:schema"))
    compile(project(":vocabularies:imasparql"))
    compile(Dep.Kotlin.stdlibJdk8)
    compile(Dep.Ktor.serverNetty)
    compile(Dep.logback)
    compile(Dep.Ktor.serverCore)
    compile(Dep.Ktor.clientCommon)
    compile(Dep.Ktor.clientApache)
    compile(Dep.Ktor.clientLogging)
    testCompile(Dep.Ktor.serverTest)
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlin.Experimental"
        )
    }
}
