import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ktfmt)
}

kotlin {
    dependencies {
        implementation(projects.shared)
        implementation(compose.desktop.currentOs)
        implementation(libs.compose.runtime)
        implementation(compose.desktop.currentOs)
        implementation(libs.kotlinx.coroutinesSwing)
    }
}

ktfmt { kotlinLangStyle() }

compose.desktop {
    application {
        mainClass = "com.hanitacm.mymoviescmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.hanitacm.mymoviescmp"
            packageVersion = "1.0.0"
        }
    }
}
