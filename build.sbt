import sbt.Keys.libraryDependencies

version := "0.0.1"

scalaVersion := "2.12.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Yrangepos")


lazy val migrations = (project in file("migrations"))
  .enablePlugins(FlywayPlugin)


lazy val root = (project in file("."))
  .aggregate(migrations)


libraryDependencies ++= {
  val akkaHttpV = "10.1.0-RC2"
  val akkaV = "2.5.10"
  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.13.0",
    "io.getquill" %% "quill-async-postgres" % "2.3.2",
    "org.postgresql" % "postgresql" % "9.4.1208",
    "com.typesafe" % "config" % "1.3.2",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "org.specs2" %% "specs2-core" % "4.0.2" % Test,
    "org.specs2" %% "specs2-mock" % "4.0.2" % Test,
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.flywaydb" % "flyway-core" % "5.0.7" % Test
  )
}
