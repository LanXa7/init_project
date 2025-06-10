plugins {
    id("spring-conventions")
    id("test-conventions")
}

dependencies {
    api(projects.model)
    api(projects.common)
    ksp(libs.jimmer.ksp)
    implementation(libs.jimmer.sql.kotlin)
    implementation(libs.redisson)
    implementation(libs.validation)
    implementation(libs.security)
    implementation(libs.jimmer.spring.boot.starter)
    implementation(libs.ampq)
    implementation(libs.minio)
    implementation(libs.jwt)
    implementation(libs.fastexcel)
    implementation(libs.spring.plugin)
    implementation(libs.logging)
    implementation(libs.mail)
    implementation(libs.aop)
    implementation(libs.okhttp3)
    implementation(libs.flyway)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.postgresql)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
