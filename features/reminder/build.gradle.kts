import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger)
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.compose)
}
fun getProps(propName: String): String? {
    val propsFile = project.rootProject.file("local.properties")
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        return props.getProperty(propName)
    } else {
        return null
    }
}

android {
    namespace = "com.sandev.features.reminder"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildTypes{
        debug {
            //Cloudinary Cloud Name
            buildConfigField("String","CLOUDINARY_CLOUD_NAME","\"${getProps("CLOUDINARY_CLOUD_NAME")}\"")

            //Cloudinary API KEY
            buildConfigField("String","CLOUDINARY_API_KEY","\"${getProps("CLOUDINARY_API_KEY")}\"")

            //Cloudinary API SECRET
            buildConfigField("String","CLOUDINARY_API_SECRET","\"${getProps("CLOUDINARY_API_SECRET")}\"")
        }
        release{
            //Cloudinary Cloud Name
            buildConfigField("String","CLOUDINARY_CLOUD_NAME","\"${getProps("CLOUDINARY_CLOUD_NAME")}\"")

            //Cloudinary API KEY
            buildConfigField("String","CLOUDINARY_API_KEY","\"${getProps("CLOUDINARY_API_KEY")}\"")

            //Cloudinary API SECRET
            buildConfigField("String","CLOUDINARY_API_SECRET","\"${getProps("CLOUDINARY_API_SECRET")}\"")

        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.foundation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Project Modules
    implementation(project(":domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))


    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.lifecycle.compose)
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.kapt)
    implementation(libs.hilt.compose.navigation)

    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    // Coil for image loading
    implementation(libs.coil.compose)
    implementation(libs.coil)

    implementation(libs.cloudinary.android)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.gson)
}