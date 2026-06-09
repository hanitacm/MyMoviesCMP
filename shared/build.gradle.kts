import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.koinCompiler)
    alias(libs.plugins.ktfmt)
}

ktfmt { kotlinLangStyle() }

kotlin {
    androidLibrary {
        namespace = "com.hanitacm.mymoviescmp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        compilerOptions { jvmTarget = JvmTarget.JVM_11 }

        androidResources { enable = true }
    }

    jvm {
        attributes.attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm)
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.napier)
        }
        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies { implementation(libs.ktor.client.darwin) }
        commonTest.dependencies {
            implementation(libs.koin.test)
            implementation(libs.kotlin.test)
        }
    }
}
