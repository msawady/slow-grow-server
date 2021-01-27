name := "slow-grow-server"

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
  scalafmtOnCompile := true,
  libraryDependencies += Dependencies.scalaTest % Test,
  libraryDependencies ++= Dependencies.baseDependencies
)

lazy val root = (project in file("."))
  .settings(baseSettings)
  .aggregate(domainData, application)

lazy val domainData = (project in file("domain-data"))
  .settings(baseSettings)

lazy val application = (project in file("application"))
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Dependencies.applicationDependencies
  )
  .dependsOn(domainData)

lazy val infra = (project in file("infra"))
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Dependencies.infraDependencies
  )
  .dependsOn(application)
