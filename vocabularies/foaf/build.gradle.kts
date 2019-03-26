
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id(Dep.GradlePlugin.jfrog) version Versions.bintray
    id(Dep.GradlePlugin.shadow) version Versions.shadow
    `maven-publish`
    kotlin("jvm")
}

group = "net.subroh0508.sparkt.vocabularies"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(Dep.Kotlin.stdlibJdk8)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    baseName = project.name
    classifier = null
}

val publicationName = "ToMavenPublication"

publishing {
    publications.invoke {
        register(publicationName, MavenPublication::class) {
            groupId = project.group as String
            artifactId = project.name
            version = Packages.versionName
            artifact(shadowJar)
            pom.withXml {
                asNode().appendNode("name", "SPARkt-foaf")
                asNode().appendNode("description", "SPARQL vocabularies from foaf")
                asNode().appendNode("url", Packages.githubUrl)
                asNode().appendNode("dependencies").let { depNode ->
                    configurations.compile.get().allDependencies.forEach {
                        depNode.appendNode("dependency").apply {
                            appendNode("groupId", it.group)
                            appendNode("artifactId", it.name)
                            appendNode("version", it.version)
                        }
                    }
                }
            }
        }
    }
}

bintray {
    user = project.findProperty("bintray_user") as String
    key = project.findProperty("bintray_key") as String

    publish = true
    setPublications(publicationName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "SPARkt-foaf"
        setLicenses("MIT")
        websiteUrl = Packages.siteUrl
        vcsUrl = "${Packages.githubUrl}.git"
        issueTrackerUrl = "${Packages.githubUrl}/issues"
        publicDownloadNumbers = true
        version.name = Packages.versionName
        version.released = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date())
    })
}

tasks.getting(GradleBuild::class) {
    dependsOn(shadowJar)
}

tasks.getting(Test::class) {
    testLogging.showStandardStreams = true
}

tasks.getting(GenerateMavenPom::class) {
    destination = file("$buildDir/libs/${shadowJar.archiveFileName}.pom")
}
