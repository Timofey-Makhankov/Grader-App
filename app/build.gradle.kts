plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id("jacoco")
}

android {
    signingConfigs {
        // Only for Testing purposes ONLY (commented out)
        /*
        debug {
            storeFile file("C:\\Users\\Timofey\\Projects\\personal\\Grader-App\\keystore.jks")
            storePassword "123456"
            keyPassword "123456"
            keyAlias "key0"
        }
        */
        getByName("debug") { }
    }
    namespace = "ch.timofey.grader"
    compileSdk = 35

    defaultConfig {
        applicationId = "ch.timofey.grader"
        testApplicationId = "ch.timofey.grader"
        minSdk = 28
        targetSdk = 35
        versionCode = 125
        versionName = "0.12.5-Beta"
        testApplicationId = "ch.timofey.grader.test"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
        resourceConfigurations += listOf("en", "de", "ru")
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    sourceSets.named("androidTest") {
        // Adds exported schema location as test app assets.
        assets.srcDir("$projectDir/schemas")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            // (Deprecated warning—see note below)
            versionNameSuffix = ":debug"
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }
    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
    splits {
        abi { isEnable = true; isUniversalApk = true }
    }
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.register("jacocoTestReport", org.gradle.testing.jacoco.tasks.JacocoReport::class) {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoHtml"))
    }
    sourceDirectories.setFrom(files(
        "$projectDir/app/src/main/java",
        "$projectDir/app/src/main/kotlin"
    ))
    classDirectories.setFrom(
        fileTree("$buildDir/tmp/kotlin-classes/debug") {
            exclude("**/*Test*.*", "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*")
        }
    )
    executionData.setFrom(files("$buildDir/jacoco/testDebugUnitTest.exec"))
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles = listOf(rootProject.layout.projectDirectory.file("stability_config.conf"))
}

dependencies {
    // Production dependencies
    implementation("androidx.test:monitor:1.7.2")
    implementation("androidx.compose.ui:ui-test-junit4-android:1.7.7")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    val compose_ui_version = "1.7.6"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:$compose_ui_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.appcompat:appcompat-resources:1.7.0")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Kotlin Faker
    debugImplementation("io.github.serpro69:kotlin-faker:1.16.0")

    // Mockito framework
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    /// Android Testing (instrumented)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // Force Espresso to 3.5.0 for consistency.
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
    // **Add the runner and rules:**
    androidTestImplementation("androidx.test:runner:1.5.0")
    androidTestImplementation("androidx.test:rules:1.5.0")

    implementation(platform("androidx.compose:compose-bom:2025.01.00"))
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
    implementation("androidx.compose.material:material-icons-extended")
    // Material 3
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    // Navigation
    val nav_version = "2.8.5"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    // ROOM Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.53.1")
    ksp("com.google.dagger:dagger-compiler:2.53.1")
    ksp("com.google.dagger:hilt-compiler:2.53.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    // System bars customization
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
    // SplashScreen API
    implementation("androidx.core:core-splashscreen:1.0.1")
    // Pager and Indicators - Accompanist
    val accompanist_version = "0.36.0"
    implementation("com.google.accompanist:accompanist-pager:$accompanist_version")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanist_version")
    // DataStore Preferences
    implementation("androidx.datastore:datastore:1.1.1")
    // Jetpack Glance
    implementation("androidx.glance:glance-appwidget:1.1.1")
    implementation("androidx.glance:glance-material3:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("com.google.code.gson:gson:2.11.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.6")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

hilt {
    enableAggregatingTask = true
}

// Force all configurations to use espresso-core:3.5.0 to resolve conflicts.
configurations.all {
    resolutionStrategy {
        force("androidx.test.espresso:espresso-core:3.5.0")
    }
}