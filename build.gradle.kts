import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
}

group = "me.lkdan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.apis:google-api-services-searchconsole:v1-rev20211026-1.32.1")
    implementation("com.google.api-client:google-api-client:1.33.2")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.33.1")
    implementation("com.google.api-client:google-api-client-jackson2:1.33.4")
    implementation("com.google.apis:google-api-services-analyticsreporting:v4-rev20211020-1.32.1")
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}
