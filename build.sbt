import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val scala212 = "2.12.21"

val scala213 = "2.13.18"

val scala3 = "3.3.7"

val allScalaVersions = List(scala3, scala213, scala212)

name := "perfolation"
ThisBuild / organization := "com.outr"
ThisBuild / version := "1.3.0"
ThisBuild / scalaVersion := scala3
ThisBuild / crossScalaVersions := allScalaVersions
ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")

ThisBuild / sonatypeCredentialHost := Sonatype.sonatypeCentralHost
ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeProfileName := "com.outr"
ThisBuild / licenses := Seq("MIT" -> url("https://github.com/outr/perfolation/blob/master/LICENSE"))
ThisBuild / sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "perfolation", "matt@outr.com"))
ThisBuild / homepage := Some(url("https://github.com/outr/perfolation"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/outr/perfolation"),
    "scm:git@github.com:outr/perfolation.git"
  )
)
ThisBuild / developers := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

val scalaJavaTimeVersion: String = "2.6.0"

// Dependency versions
val scalaTestVersion: String = "3.2.20"

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
    name := "perfolation",
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % scalaJavaTimeVersion,
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % scalaJavaTimeVersion,
      "org.scalatest" %%% "scalatest" % scalaTestVersion % Test
    )
  )

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .dependsOn(core)
  .settings(
    name := "perfolation-unit",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalaTestVersion % Test
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
    crossScalaVersions := List("2.13.18")
  )
