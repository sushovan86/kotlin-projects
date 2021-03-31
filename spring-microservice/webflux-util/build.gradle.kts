dependencies {

    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}