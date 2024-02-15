import com.android.build.api.variant.BuildConfigField
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
   // id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.5.21"
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.hxt5gh.frontend"
    compileSdk = 34




    defaultConfig {

        // Load app.properties file
        val appPropertiesFile = rootProject.file("app.properties")
        val appProperties = Properties()
        appProperties.load(FileInputStream(appPropertiesFile))

        // Define API_KEY constant
        buildConfigField("String", "WEB_CLINT_ID", appProperties.getProperty("WEB_CLINT_ID"))
        buildConfigField("String", "KTOR_IP_ADDRESS_One", appProperties.getProperty("KTOR_IP_ADDRESS_One"))
        buildConfigField("String", "KTOR_IP_ADDRESS_Two", appProperties.getProperty("KTOR_IP_ADDRESS_Two"))


        applicationId = "com.hxt5gh.frontend"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation ("com.google.android.gms:play-services-auth:19.2.0")

    //Viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //for Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")


    //ktor
    val ktor_version = "1.6.3"
    implementation ("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-client-serialization:$ktor_version")
    implementation ("io.ktor:ktor-client-logging:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")


    //navigation
//    val nav_version = "2.7.6"
 //   val nav_version = "2.3.4"
 //   implementation("androidx.navigation:navigation-compose:$nav_version")
  //  implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    //hilt
 //   implementation("com.google.dagger:hilt-android:2.44")
 //   kapt("com.google.dagger:hilt-android-compiler:2.44")
  val  navigation_version = "2.5.3" // was 2.5.1
   val  dagger_hilt_version = "2.44.2" // was 2.43.2

    implementation ("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$navigation_version")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:$navigation_version")
    androidTestImplementation ("androidx.navigation:navigation-testing:$navigation_version")
       implementation("androidx.navigation:navigation-compose:$navigation_version")
      implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation ("com.google.dagger:hilt-android:$dagger_hilt_version")
    kapt ("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")
    testImplementation ("com.google.dagger:hilt-android-testing:$dagger_hilt_version")
    kaptTest ("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:$dagger_hilt_version")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")



    //image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")
}

kapt {
    correctErrorTypes = true
}