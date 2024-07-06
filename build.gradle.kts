plugins {
    id("java")
}

group = "org.stone72"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly(files("libs/Nukkit-MOT-SNAPSHOT.jar"))
}

tasks.test {
    useJUnitPlatform()
}