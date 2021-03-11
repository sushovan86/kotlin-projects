import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion by extra("2.4.3")

plugins {
	id("org.springframework.boot") version "2.4.3" apply false
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30" apply false
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
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	java.sourceCompatibility = JavaVersion.VERSION_11

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
		}
	}

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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








