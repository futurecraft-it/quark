plugins {
    `java-library`
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin)
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
    implementation(libs.kache)
    implementation(libs.coroutines)

    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.serialization)

    compileOnly(libs.bundles.minecraft)

    // testImplementation(kotlin("test"))
}

//tasks.test {
//    useJUnitPlatform()
//}

kotlin {
    jvmToolchain(21)
}