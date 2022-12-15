import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
//    base
}

group = "me.sraaphorst"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // These are needed for the Java ScriptEngine kts engine.
    implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("org.jetbrains.kotlin:kotlin-script-util")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable")
//    testImplementation("org.jetbrains.kotlin:kotlin-script-runtime")
//    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
//    testImplementation("org.jetbrains.kotlin:kotlin-script-util")
//    testImplementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable")
//    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.4.10")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.4.10")
//    testRuntimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.4.10")
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