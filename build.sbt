import com.typesafe.sbt.packager.docker.Dockerfile

name := "minio-storage" // имя проекта

version := "1.1"

lazy val `minio-storage` = (project in file(".")).enablePlugins(PlayJava, PlayScala, DockerPlugin)

dockerAutoPackageJavaApplication()

javacOptions ++= Seq("-Xlint:all")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

scalaVersion := "2.12.6" // последняя версия

libraryDependencies += guice // обязательно
libraryDependencies += jcache // не обязательно, для кэширования выдачи
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test // обязательно для тестов
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test // обязательно для тестов
libraryDependencies += "org.mybatis.caches" % "mybatis-ehcache" % "1.1.0" // обязательно для mybatis
libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4" // обязательно для БД
libraryDependencies += "org.jsr107.ri" % "cache-annotations-ri-guice" % "1.0.0" // не обязательно, для кэша
libraryDependencies += "io.swagger" %% "swagger-play2" % "1.6.0" // обязательно для документации в swagger
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % "test"

libraryDependencies ++= Seq(
  javaJdbc, javaWs, cacheApi, ehcache, ws,
  "org.mybatis" % "mybatis" % "3.3.1",
  "org.mybatis" % "mybatis-guice" % "3.10",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.40",
  "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.9.6",
  "io.minio" % "minio" % "4.0.2"
)

updateOptions := updateOptions.value.withCachedResolution(true)
routesGenerator := InjectedRoutesGenerator

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")) // verbose тесты

unmanagedResourceDirectories in Compile += (baseDirectory.value / "conf")

