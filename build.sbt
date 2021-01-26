import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val allScalaVersions = List("2.12.13", "2.11.12", "2.13.4", "3.0.0-M3")
val scala2Versions = allScalaVersions.filter(_.startsWith("2."))

name := "perfolation"
organization in ThisBuild := "com.outr"
version in ThisBuild := "1.2.4-SNAPSHOT"
scalaVersion in ThisBuild := "2.13.4"
crossScalaVersions in ThisBuild := allScalaVersions
scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

publishTo in ThisBuild := sonatypePublishTo.value
sonatypeProfileName in ThisBuild := "com.outr"
publishMavenStyle in ThisBuild := true
licenses in ThisBuild := Seq("MIT" -> url("https://github.com/outr/perfolation/blob/master/LICENSE"))
sonatypeProjectHosting in ThisBuild := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "perfolation", "matt@outr.com"))
homepage in ThisBuild := Some(url("https://github.com/outr/perfolation"))
scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/outr/perfolation"),
    "scm:git@github.com:outr/perfolation.git"
  )
)
developers in ThisBuild := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

// Dependency versions
val scalatestVersion = "3.2.4-M1"

lazy val root = project.in(file("."))
  .aggregate(
    core.js, core.jvm, core.native,
    unit.js, unit.jvm, unit.native
  )
  .settings(
    name := "perfolation",
    publish := {},
    publishLocal := {}
  )

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .settings(
    name := "perfolation"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jsSettings(
    Test / sources := {
      if (isDotty.value) {        // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        (Test / sources).value
      }
    },
    libraryDependencies ++= (
      if (isDotty.value) {      // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        List("org.scalatest" %%% "scalatest" % scalatestVersion % "test")
      }
    )
  )
  .nativeSettings(
    crossScalaVersions := scala2Versions,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .dependsOn(core)
  .settings(
    name := "perfolation-unit"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jsSettings(
    Test / sources := {
      if (isDotty.value) {        // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        (Test / sources).value
      }
    },
    libraryDependencies ++= (
      if (isDotty.value) {      // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        List("org.scalatest" %%% "scalatest" % scalatestVersion % "test")
      }
    )
  )
  .nativeSettings(
    crossScalaVersions := scala2Versions,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )

lazy val benchmarks = project
  .in(file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .dependsOn(core.jvm)
  .settings(
    libraryDependencies ++= Seq(
      "pl.project13.scala" % "sbt-jmh-extras" % "0.3.7"
    ),
    crossScalaVersions := List("2.13.4")
  )
