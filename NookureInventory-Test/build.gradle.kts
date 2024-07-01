plugins {
  id("java")
  id("com.github.johnrengelman.shadow") version ("8.1.1")
  id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "com.nookure.core.inv"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
  implementation("io.pebbletemplates:pebble:3.2.2")
  implementation("org.jetbrains:annotations:24.1.0")
  implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
  implementation("org.glassfish.jaxb:jaxb-runtime:4.0.5")
  implementation(project(":"))
}

tasks {
  runServer {
    // Configure the Minecraft version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    minecraftVersion("1.20.4")
  }
}