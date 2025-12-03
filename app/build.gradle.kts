import java.util.Properties

plugins {
    id ("com.android.application")
    id ("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.parcelize")
    id ("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id ("dagger.hilt.android.plugin")
}

android {
    signingConfigs {
        var localProps = Properties()
        val localPropsFile = File("local.properties")
        if (localPropsFile.exists() && localPropsFile.isFile) {
            localPropsFile.inputStream().use {
                localProps.load(it)
            }
        }

        create("release") {
                storeFile = file(localProps.getProperty("keystore"))
                storePassword = localProps.getProperty("storePassword")
                keyAlias = localProps.getProperty("keyAlias")
                keyPassword = localProps.getProperty("keyPassword")
        }
        getByName("debug") {

        }
    }
    namespace = "ch.timofey.grader"
    compileSdk = 36

    defaultConfig {
        applicationId = "ch.timofey.grader"
        minSdk = 28
        targetSdk = 36
        versionCode = 123
        versionName = "0.12.3-Beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        androidResources {
            localeFilters.addAll(listOf("en", "de", "ru"))
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
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
            // Point to Config
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    splits {
        abi {
            isEnable = true
            isUniversalApk = true
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles = listOf(rootProject.layout.projectDirectory.file("stability_config.conf"))
}

dependencies {
    implementation ("androidx.test:monitor:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation ("androidx.activity:activity-compose:1.12.0")
    implementation ("androidx.compose.ui:ui:1.9.5")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.9.5")
    implementation ("androidx.core:core-ktx:1.17.0")
    implementation ("androidx.appcompat:appcompat:1.7.1")
    implementation ("androidx.appcompat:appcompat-resources:1.7.1")


    // Unit Testing
    testImplementation ("junit:junit:4.13.2")

    // Kotlin Faker
    debugImplementation ("io.github.serpro69:kotlin-faker:1.16.0")

    //Mockito framework
    testImplementation ("org.mockito:mockito-core:5.20.0")
    //mockito-kotlin
    testImplementation ("org.mockito.kotlin:mockito-kotlin:6.1.0")

    // Android Testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation ( platform("androidx.compose:compose-bom:2025.11.01") )

    androidTestImplementation ("androidx.test.ext:junit:1.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.9.5")

    implementation( platform("androidx.compose:compose-bom:2025.11.01") )

    debugImplementation ("androidx.compose.ui:ui-tooling:1.9.5")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.9.5")

    implementation ("androidx.compose.material:material-icons-extended")

    // Material 3
    implementation ("androidx.compose.material3:material3:1.4.0")
    implementation ("androidx.compose.material3:material3-window-size-class:1.4.0")

    // Navigation
    implementation ("androidx.navigation:navigation-compose:2.9.6")

    // ROOM Database
    implementation ("androidx.room:room-runtime:2.8.4")
    ksp ("androidx.room:room-compiler:2.8.4")
    implementation ("androidx.room:room-ktx:2.8.4")
    testImplementation ("androidx.room:room-testing:2.8.4")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.57.2")

    ksp ("com.google.dagger:dagger-compiler:2.57.2") // Dagger compiler
    ksp ("com.google.dagger:hilt-compiler:2.57.2")   // Hilt compiler

    implementation ("androidx.hilt:hilt-navigation-compose:1.3.0")

    // MVVM
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // system bars customization
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    // SplashScreen API
    implementation ("androidx.core:core-splashscreen:1.2.0")

    // Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.36.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.36.0")

    // DataStore Preferences
    implementation ("androidx.datastore:datastore:1.2.0")

    // Jetpack Glance
    //implementation "androidx.glance:glance:1.0.0"
    implementation ("androidx.glance:glance-appwidget:1.1.1")
    implementation ("androidx.glance:glance-material3:1.1.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.4.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    implementation ("com.google.code.gson:gson:2.13.2")
}

hilt {
    enableAggregatingTask = true
}
