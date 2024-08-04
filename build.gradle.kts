plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.7.1"
	id("xyz.jpenilla.run-paper") version "2.2.3" // Adds runServer and runMojangMappedServer tasks for testing
}

group = "com.rifledluffy.chairs"
version = "7.0.0-SNAPSHOT"
description = "Chairs but Rifle's way."
// this is the minecraft major version. If you need a subversion like 1.20.1,
// change it in the dependencies section as this is also used as the api version of the plugin.yml
val mcVersion by extra("1.21")
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

java {
  // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenLocal()

    //paper
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    //world guard
    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.1.0-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.1.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8") // caches
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

    // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
    // See https://openjdk.java.net/jeps/247 for more information.
    options.release.set(21)
  }
  
  processResources {
      filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything

      expand("version" to project.version,
          "description" to project.description,
          "apiVersion" to mcVersion)
  }
}