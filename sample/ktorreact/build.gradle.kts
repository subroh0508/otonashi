plugins {
    application
    kotlin("jvm")
}

group = "net.subroh0508.otonashi.sample"
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
    compile(Dep.Kotlin.stdlibJdk8)
    compile(Dep.Ktor.serverNetty)
    compile(Dep.logback)
    compile(Dep.Ktor.serverCore)
    testCompile(Dep.Ktor.serverTest)
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
