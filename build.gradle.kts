plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    id("jacoco")
}

group = "com.robotutor.iot"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()

    fun githubMavenRepository(name: String) {
        maven {
            url = uri("https://maven.pkg.github.com/IOT-echo-system/$name")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    githubMavenRepository("robotutor-tech-utils")
    githubMavenRepository("kafka-starter")
    githubMavenRepository("web-client-starter")
    githubMavenRepository("logging-starter")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

// Jacoco configuration

jacoco {
    toolVersion = "0.8.12"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                minimum = BigDecimal(0.52)
            }
            limit {
                counter = "BRANCH"
                minimum = BigDecimal(1)
            }
            limit {
                counter = "LINE"
                minimum = BigDecimal(0.53)
            }
            limit {
                counter = "METHOD"
                minimum = BigDecimal(0.41)
            }
            limit {
                counter = "CLASS"
                minimum = BigDecimal(0.39)
            }
        }
    }
}
