import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `java-library`
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.serialization)
}

group = "it.futurecraft.quark"
version = libs.versions.project.get()

repositories {
    mavenCentral()

    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(libs.coroutines)

    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.serialization)

    compileOnly(libs.bundles.minecraft)
}

kotlin {
    jvmToolchain(21)
}

dokka {
    moduleName = "quark"

    dokkaPublications.html {
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
        outputDirectory.set(layout.buildDirectory.dir("docs"))
    }

    pluginsConfiguration.html {
        footerMessage.set("FUTURECRAFT (c) 2025")
        customStyleSheets.from("assets/logo-styles.css")
        customAssets.from("assets/logo-icon.png", "assets/logo-icon.svg")
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "quark", version.toString())

    pom {
        name = "Quark"
        description = "A paper minecraft library."
        inceptionYear = "2025"
        url = "https://github.com/futurecraft-it/quark/"
        licenses {
            license {
                name = "GNU Affero General Public License v3.0"
                url = "https://www.gnu.org/licenses/agpl-3.0.txt"
                distribution = "https://www.gnu.org/licenses/agpl-3.0.txt"
            }
        }
        developers {
            developer {
                id = "danieleguglietti"
                name = "Daniele Guglietti"
                url = "https://github.com/danieleguglietti/"
            }
        }
        scm {
            url = "https://github.com/futurecraft-it/quark/"
            connection = "scm:git:git://github.com/futurecraft-it/quark/.git"
            developerConnection = "scm:git:ssh://git@github.com/futurecraft-it/quark/.git"
        }
    }
}
