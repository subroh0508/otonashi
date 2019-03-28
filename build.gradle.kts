
import com.jfrog.bintray.gradle.BintrayExtension
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id(Dep.GradlePlugin.jfrog) version Versions.bintray
    `maven-publish`
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/subroh0508/maven")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

subprojects {
    apply {
        plugin("maven-publish")
        plugin(Dep.GradlePlugin.jfrog)
    }

    ext["bintrayTask"] = { libraryName: String ->
        val sourceSets: SourceSetContainer by extensions
        val sourceJar by tasks.registering(Jar::class) {
            classifier = "sources"
            from(sourceSets.named<SourceSet>("main").get().allSource)
        }

        val publicationName = "ToMavenPublication"

        publishing {
            publications.invoke {
                register(publicationName, MavenPublication::class) {
                    groupId = project.group as String
                    artifactId = project.name
                    version = Packages.versionName
                    artifact(sourceJar.get())
                    from(components["java"])
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
                name = libraryName
                setLicenses("MIT")
                websiteUrl = Packages.siteUrl
                vcsUrl = "${Packages.githubUrl}.git"
                issueTrackerUrl = "${Packages.githubUrl}/issues"
                publicDownloadNumbers = true
                version.name = Packages.versionName
                version.released = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date())
            })
        }
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}


