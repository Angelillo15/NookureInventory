# Nookure Inventory
This is a simple library that aims to provide a simple way to create inventory for your
Paper plugin ([Spigot support*](#spigot-support) using a XML based file with a template engine,
if you are a web developer you will feel at home with terms like `components`.

The main point of this library is to provide a way to create inventory without the need to
write a lot of Java code and to provide a more user-friendly way to create inventory.

## Getting started
Adding the library to your project is simple, just add the following to your build script:

<details open>
<summary>Gradle Kotlin DSL</summary>

```kotlin
repositories {
    maven("https://maven.nookure.com")
}

dependencies {
    implementation("com.nookure.core:NookCore-Inventory:<version>")
}
```
</details>


<details>
<summary>Gradle Groovy DSL</summary>


```groovy
repositories {
    maven { url 'https://maven.nookure.com' }
}

dependencies {
    implementation 'com.nookure.core:NookCore-Inventory:<version>'
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<repositories>
    <repository>
        <id>nookure</id>
        <url>https://maven.nookure.com</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.nookure.core</groupId>
        <artifactId>NookCore-Inventory</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```
</details>


## Spigot support
This library requires the use of the [Adventure](https://docs.advntr.dev/) library, which is included in Paper 1.16.5 and above. If you are using Spigot, you will need to include the Adventure library in your project. More information [here](https://docs.advntr.dev/platform/bukkit.html).
You will also need to include MiniMessage in your project. More information [here](https://docs.advntr.dev/minimessage/api.html).