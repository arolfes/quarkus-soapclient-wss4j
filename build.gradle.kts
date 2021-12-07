plugins {
    kotlin("jvm") 
    kotlin("plugin.allopen")
    id("io.quarkus")
    id("com.github.bjornvester.wsdl2java")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val groupId: String by project
val projectVersion: String by project

group = groupId
version = projectVersion

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = java.sourceCompatibility
val jvmTargetVersion = java.sourceCompatibility.toString()

val assertjVersion: String by project
val cxfSecurityVersion: String by project
val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val quarkusCxfVersion: String by project
val saajApiVersion: String by project
val saajImplVersion: String by project
val wiremockVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-jackson")

    implementation("io.quarkiverse.cxf:quarkus-cxf:$quarkusCxfVersion")
    implementation("org.apache.cxf:cxf-rt-ws-security:$cxfSecurityVersion")
    implementation("com.sun.xml.messaging.saaj:saaj-impl:$saajImplVersion")
    implementation("javax.xml.soap:saaj-api:$saajApiVersion")

    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("com.github.tomakehurst:wiremock-jre8:$wiremockVersion")
}


allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTargetVersion
        kotlinOptions.javaParameters = true
    }

    withType<Test> {

        testLogging {
            showExceptions = true
            showStandardStreams = true
            events(
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
            )
        }
    }

}
