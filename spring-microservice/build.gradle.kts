import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion: String by project
val springCloudVersion: String by project

plugins {
    idea
    id("org.springframework.boot") version "2.5.3" apply false
    id("com.gorylenko.gradle-git-properties") version "2.2.4" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21" apply false
    kotlin("kapt") version "1.5.21" apply false
}

idea {
    module {
        isDownloadSources = true
    }
}

allprojects {

    group = "com.chak.sc"
    version = "0.0.1"

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {

    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.gorylenko.gradle-git-properties")

    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }

    dependencies {

        implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.11")
        implementation("com.michael-bull.kotlin-result:kotlin-result-coroutines:1.1.11")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("org.springframework.boot:spring-boot-starter-actuator")

        val developmentOnly by configurations
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
