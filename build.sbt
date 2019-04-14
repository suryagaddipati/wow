import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "wow",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "com.github.seratch" %% "awscala" % "0.8.1",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.9",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.9",
      "org.scala-sbt" % "command" % "0.12.0"

    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
