plugins {
  `java-library`
  kotlin("jvm")
  id("org.jlleitschuh.gradle.ktlint")
}

val kotestVersion: String by project
val coroutineVersion: String by project
val cronUtils: String by project

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
  implementation("com.cronutils:cron-utils:$cronUtils")

  testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
  testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
  testImplementation("io.kotest:kotest-framework-api-jvm:$kotestVersion")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
}

tasks {
  // TODO Add ktlintFormat dependency on check for auto linter formatting
  // check {
  //   dependsOn("ktlintFormat")
  // }

  test {
    dependsOn("kotest")
  }
}
