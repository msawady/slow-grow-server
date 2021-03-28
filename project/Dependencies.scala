import sbt._

object Dependencies {

  val scalaVersion = "2.13.5"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2"

  lazy val baseDependencies = Seq(
    "org.wvlet.airframe" %% "airframe-log" % "21.1.1"
  )

  lazy val applicationDependencies = Seq(
    "org.typelevel" %% "cats-effect" % "3.0.0-M5"
  )

  lazy val infraDependencies = Seq(
    // using java-sdk because scala-sdk is no longer maintained and scala2.13 artifacts is not published yet.
    "net.jacobpeterson" % "alpaca-java" % "6.1",
    "org.wvlet.airframe" % "airframe_2.13" % "21.1.1"
  )
}
