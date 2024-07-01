plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version ("8.1.1")
  id("maven-publish")
  id("java-library")
}

group = "com.nookure.core.inv"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  api("io.pebbletemplates:pebble:3.2.2")
  api("org.jetbrains:annotations:24.1.0")
  api("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
  api("org.glassfish.jaxb:jaxb-runtime:4.0.5")
  api("com.github.ben-manes.caffeine:caffeine:3.1.8")

  compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}

allprojects {
  tasks {
    withType<JavaCompile> {
      options.encoding = "UTF-8"
    }

    withType<Javadoc> {
      options.encoding = "UTF-8"
    }
  }
}