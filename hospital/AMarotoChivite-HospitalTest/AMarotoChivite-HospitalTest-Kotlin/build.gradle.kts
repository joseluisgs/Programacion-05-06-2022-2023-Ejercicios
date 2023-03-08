import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // KOTLIN OPCIÓN 1
    // JUNIT
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    // Mockk para test
    testImplementation("io.mockk:mockk:1.13.3")

    //KOTLIN OPCION 2 (unitarios propia de KOTLIN)
    //testImplementation(kotlin("test"))

    // FULL JAVA KIT
    /*  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.0") // Si queremos utilizar Mock, este comentamos
        //testImplementation ("org.mockito:mockito-inline:5.1.1")
        //testImplementation ("org.mockito:mockito-junit-jupiter:5.1.1")*/
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}