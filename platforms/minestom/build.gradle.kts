/*
 *     This file is part of UnifiedMetrics.
 *
 *     UnifiedMetrics is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     UnifiedMetrics is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with UnifiedMetrics.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven")
    maven("https://jitpack.io")
}

dependencies {
    api(project(":unifiedmetrics-core"))

    compileOnly("net.minestom:minestom-snapshots:ebaa2bbf64")
    testImplementation("net.minestom:minestom-snapshots:ebaa2bbf64")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("retrofit2", "dev.cubxity.plugins.metrics.libs.retrofit2")
        relocate("com.charleskorn", "dev.cubxity.plugins.metrics.libs.com.charleskorn")
        relocate("com.influxdb", "dev.cubxity.plugins.metrics.libs.com.influxdb")
        relocate("okhttp", "dev.cubxity.plugins.metrics.libs.okhttp")
        relocate("okio", "dev.cubxity.plugins.metrics.libs.okio")
        relocate("io.prometheus", "dev.cubxity.plugins.metrics.libs.io.prometheus")
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set("UnifiedMetrics")
                description.set("UnifiedMetrics is a fully-featured free and open-source metrics collection plugin for Minecraft servers.")
                url.set("https://github.com/Cubxity/UnifiedMetrics/")

                licenses {
                    license {
                        name.set("GNU Lesser General Public License v3.0")
                        url.set("https://github.com/Cubxity/UnifiedMetrics/blob/dev/0.3.x/COPYING.LESSER")
                    }
                }
                developers {
                    developer {
                        id.set("cubxity")
                        name.set("Cubxity")
                        email.set("contact@cubxity.dev")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Cubxity/UnifiedMetrics.git")
                    developerConnection.set("scm:git:ssh://github.com/Cubxity/UnifiedMetrics.git")
                    url.set("https://github.com/Cubxity/UnifiedMetrics/")
                }
            }
        }
    }
}

