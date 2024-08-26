plugins {
  id("java")
  id("maven-publish")
  id("java-library")
  alias(libs.plugins.shadow)
  alias(libs.plugins.git.publish)
  alias(libs.plugins.grgit)
}

group = "com.nookure.core"
version = "1.0.0-${grgit.head().abbreviatedId}"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  api(libs.pebble)
  api(libs.annotations)
  api(libs.jakarta.xml.bind.api)
  api(libs.jaxb.runtime)
  api(libs.caffeine)
  compileOnly(libs.authlib)
  compileOnly(libs.paper.api)
  api(libs.google.auto.service)
  annotationProcessor(libs.google.auto.service)
  testImplementation(platform(libs.junit.bom))
  testImplementation(libs.gson)
  testImplementation(libs.junit.jupiter)
}

tasks.test {
  useJUnitPlatform()
}

publishing {
  repositories {
    maven {
      name = "local"
      url = uri("${rootProject.rootDir}/maven")
    }
  }

  publications {
    create<MavenPublication>("mavenJava") {
      groupId = project.group.toString()
      artifactId = "NookCore-Inventory"
      version = rootProject.version.toString()

      from(components["java"])
    }
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

gitPublish {
  repoUri = "https://github.com/Nookure/maven.git"
  branch = "main"
  fetchDepth = null
  commitMessage = "NookCore-Inventory $version"

  contents {
    from("${rootProject.rootDir}/maven")
  }

  preserve {
    include("**")
  }
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

java {
  withJavadocJar()
  withSourcesJar()
}

tasks {
  withType<Javadoc> {
    val o = options as StandardJavadocDocletOptions
    o.encoding = "UTF-8"
    o.source = "21"
  }
}