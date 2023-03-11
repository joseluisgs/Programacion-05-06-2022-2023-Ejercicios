// Para kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Para kotlin
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

//Para hacer un JAR ejecutable
tasks.jar {
    manifest {
        // Aquí se pone el nombre del fichero que tiene el método main que queremos que se lance
        // si se llama main.kt, se pone MainKt, si se llama Otro.kt, se pone OtroKt
        // si está dentro de una carpeta se hace una ruta -> simulacionCine.SimulacionCineKt
        attributes["Main-Class"] = "Ficheros.Main"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

