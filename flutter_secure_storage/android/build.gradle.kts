plugins {
    id("com.android.library")
    jacoco
}

group = "com.it_nomads.fluttersecurestorage"
version = "1.0-SNAPSHOT"

jacoco {
    toolVersion = "0.8.12"
}

android {
    namespace = "com.it_nomads.fluttersecurestorage"

    buildFeatures {
        buildConfig = true
    }

    compileSdk = 37

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = 24
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all { test ->
                test.extensions.configure(JacocoTaskExtension::class.java) {
                    isIncludeNoLocationClasses = true
                    excludes = listOf("jdk.internal.*")
                }
            }
        }
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required = true
        html.required = false
    }

    val fileFilter = listOf("**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*")
    val debugTree = fileTree(layout.buildDirectory.dir("intermediates/javac/debug").get().asFile) {
        exclude(fileFilter)
    }

    sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(layout.buildDirectory.get().asFile) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/*.exec",
            )
        },
    )
}

dependencies {
    implementation("com.google.crypto.tink:tink-android:1.23.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.16.1")
}
