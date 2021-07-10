plugins {
  kotlin("jvm") version "1.5.10"
  id("io.kotest") version "0.3.8"
  id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

group = "io.alienhead.kronik"
version = "0.0.1"

allprojects {
  repositories {
    mavenCentral()
  }
}

// configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//   disabledRules.set(setOf("final-newline"))
// }
