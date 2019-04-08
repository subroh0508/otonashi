
import com.jfrog.bintray.gradle.BintrayExtension

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
        maven("https://kotlin.bintray.com/ktor")
    }
}

subprojects {
    apply {
        plugin("maven-publish")
        plugin(Dep.GradlePlugin.jfrog)
    }

    ext["bintrayTask"] = { libraryName: String, versionName: String ->
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
                    version = versionName
                    pom {
                        url.set(Packages.githubUrl)
                        licenses {
                            license {
                                name.set("The MIT License")
                                url.set("https://opensource.org/licenses/MIT")
                                distribution.set("repo")
                            }
                        }
                        developers {
                            developer {
                                id.set("subroh0508")
                                name.set("Subroh Nishikori")
                                email.set("subroh.0508@gmail.com")
                            }
                        }
                        scm {
                            url.set(Packages.githubUrl)
                        }
                    }
                    artifact(sourceJar.get())
                    from(components["java"])
                }
            }
        }

        bintray {
            user = project.findProperty("bintray_user") as String
            key = project.findProperty("bintray_key") as String

            publish = false
            setPublications(publicationName)
            pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
                repo = "maven"
                name = libraryName
                setLicenses("MIT")
                websiteUrl = Packages.siteUrl
                vcsUrl = "${Packages.githubUrl}.git"
                issueTrackerUrl = "${Packages.githubUrl}/issues"
                publicDownloadNumbers = true
                version.name = versionName
                //version.released = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.JAPAN).format(Date())
            })
        }
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}


