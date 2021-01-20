name:= "slow-grow-server"

version := "0.0.1"

lazy val baseSettings = Seq(
  organization := "com.msawady",
  scalaVersion := Dependencies.scalaVersion,
  version := (version in ThisBuild).value,
  scalacOptions := Seq(
    "-Xfatal-warnings",
    "-deprecation",
    "-unchecked",
    "-explaintypes",
    "-Xlint"
  ),
  publishArtifact in packageDoc := false,
  scalafmtOnCompile := true
)



lazy val root = (project in file("."))
  .settings(baseSettings)
  .settings(
    libraryDependencies += Dependencies.scalaTest % Test
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
