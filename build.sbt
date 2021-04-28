import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val allScalaVersions = List("2.12.13", "2.11.12", "2.13.5", "3.0.0-RC2", "3.0.0-RC3")
val scala2Versions = allScalaVersions.filter(_.startsWith("2."))

name := "perfolation"
ThisBuild / organization := "com.outr"
ThisBuild / version := "1.2.7"
ThisBuild / scalaVersion := "2.13.5"
ThisBuild / crossScalaVersions := allScalaVersions
ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")

ThisBuild / publishTo := sonatypePublishTo.value
ThisBuild / sonatypeProfileName := "com.outr"
ThisBuild / publishMavenStyle := true
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

// Dependency versions
val testyVersion: String = "1.0.5"

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
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies ++= Seq(
      "com.outr" %%% "testy" % testyVersion % Test
    )
  )
  .jsSettings(
    Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
  )
  .nativeSettings(
    crossScalaVersions := scala2Versions
  )

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .dependsOn(core)
  .settings(
    name := "perfolation-unit",
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies ++= Seq(
      "com.outr" %%% "testy" % testyVersion % Test
    )
  )
  .jsSettings(
    Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
  )
  .nativeSettings(
    crossScalaVersions := scala2Versions
  )

lazy val benchmarks = project
  .in(file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .dependsOn(core.jvm)
  .settings(
    libraryDependencies ++= Seq(
      "pl.project13.scala" % "sbt-jmh-extras" % "0.3.7"
    ),
    crossScalaVersions := List("2.13.5")
  )
