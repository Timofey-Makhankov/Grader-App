plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization") version ("1.9.0")
    id ("org.jetbrains.kotlin.plugin.parcelize")
    id ("dagger.hilt.android.plugin")
    id ("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.plugin.compose")
}

android {
    signingConfigs {
        // Only for Testing purposes ONLY
        /*debug {
            storeFile file("C:\\Users\\Timofey\\Projects\\personal\\Grader-App\\keystore.jks")
            storePassword "123456"
            keyPassword "123456"
            keyAlias "key0"
        }*/
        getByName("debug") {

        }
    }
    namespace = "ch.timofey.grader"
    compileSdk = 35

    defaultConfig {
        applicationId = "ch.timofey.grader"
        minSdk = 28
        targetSdk = 35
        versionCode = 123
        versionName = "0.12.3-Beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            versionNameSuffix = ":debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
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
    implementation ("androidx.test:monitor:1.7.2")
    val compose_ui_version = "1.7.6"
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("androidx.activity:activity-compose:1.9.3")
    implementation ("androidx.compose.ui:ui:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation ("androidx.core:core-ktx:1.15.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.appcompat:appcompat-resources:1.7.0")


    // Unit Testing
    testImplementation ("junit:junit:4.13.2")

    // Kotlin Faker
    debugImplementation ("io.github.serpro69:kotlin-faker:1.16.0")

    //Mockito framework
    testImplementation ("org.mockito:mockito-core:5.14.2")
    //mockito-kotlin
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.4.0")

    // Android Testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation ( platform("androidx.compose:compose-bom:2024.12.01") )

    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_ui_version")

    implementation( platform("androidx.compose:compose-bom:2024.12.01") )

    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_ui_version")

    implementation ("androidx.compose.material:material-icons-extended")

    // Material 3
    implementation ("androidx.compose.material3:material3:1.3.1")
    implementation ("androidx.compose.material3:material3-window-size-class:1.3.1")

    // Navigation
    val nav_version = "2.8.5"
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    // ROOM Database
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    ksp ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    testImplementation ("androidx.room:room-testing:$room_version")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.53.1")

    ksp ("com.google.dagger:dagger-compiler:2.53.1") // Dagger compiler
    ksp ("com.google.dagger:hilt-compiler:2.53.1")   // Hilt compiler

    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // MVVM
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // system bars customization
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    // SplashScreen API
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // Pager and Indicators - Accompanist
    val accompanist_version = "0.36.0"
    implementation ("com.google.accompanist:accompanist-pager:$accompanist_version")
    implementation ("com.google.accompanist:accompanist-pager-indicators:$accompanist_version")

    // DataStore Preferences
    implementation ("androidx.datastore:datastore:1.1.1")

    // Jetpack Glance
    //implementation "androidx.glance:glance:1.0.0"
    implementation ("androidx.glance:glance-appwidget:1.1.1")
    implementation ("androidx.glance:glance-material3:1.1.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    //implementation "org.jetbrains.dokka:android-documentation-plugin:1.9.0"

    implementation ("com.google.code.gson:gson:2.11.0")
}

hilt {
    enableAggregatingTask = true
}
