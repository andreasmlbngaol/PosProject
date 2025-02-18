import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization") version "2.0.21"
}

group = "com.dableyu.posproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    // Barcode Scan
    implementation("com.google.zxing:core:3.5.2")

    // Camera Access
    implementation("org.bytedeco:opencv-platform:4.5.5-1.5.7")

    // NavHost
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // ViewModel
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    // ViewModelScope
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")

    // Koin
    api("io.insert-koin:koin-core:3.6.0-Beta4")
    implementation("io.insert-koin:koin-compose:1.2.0-Beta4")
    implementation("io.insert-koin:koin-compose-viewmodel:1.2.0-Beta4")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PosProject"
            packageVersion = "1.0.0"
        }
    }
}

