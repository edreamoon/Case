plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}

dependencies {
    implementation(libs.symbol.processing.api)//引入ksp
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
}