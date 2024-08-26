plugins {
  id("java")
  id("java-library")
  alias(libs.plugins.shadow)
  alias(libs.plugins.yaml.paper)
  alias(libs.plugins.run.paper)
}

group = "com.nookure.inv"
val pluginVersion = "1.0.0"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://maven.nookure.com/")
}

tasks {
  runServer {
    minecraftVersion("1.21.1")
  }
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
  api(libs.bundles.nook.core)
  api(libs.gson)
  api(libs.caffeine)
  api(libs.google.guice)
  api(libs.google.guice.assisted)
  api(libs.google.auto.value.annotations)
  api(libs.google.auto.service)
  annotationProcessor(libs.google.auto.value.processor)
  annotationProcessor(libs.google.auto.service)
  api(project(":"))
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
  targetCompatibility = JavaVersion.VERSION_21
  sourceCompatibility = JavaVersion.VERSION_21
}

paper {
  name = "NookureInventory"
  version = "$pluginVersion-${rootProject.version}"
  description = "An advanced template engine for Minecraft Inventory GUIs."
  authors = listOf("Angelillo15", "Nookure")
  main = "com.nookure.inv.InventoryPlugin"
  apiVersion = "1.19"
  website = "https://nookure.com"
}

tasks.shadowJar {
  archiveFileName.set("NookureInventory-$version.jar")
  relocate("org.spongepowered.configurate", "com.nookure.inv.configurate")
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
  javaLauncher = javaToolchains.launcherFor {
    vendor = JvmVendorSpec.JETBRAINS
    languageVersion = JavaLanguageVersion.of(21)
  }
  jvmArgs("-XX:+AllowEnhancedClassRedefinition", "-XX:+AllowRedefinitionToAddDeleteMethods")
  systemProperties["nookure.inventories.debug"] = "true"
}