import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "perfolation"
organization in ThisBuild := "com.outr"
version in ThisBuild := "1.2.3"
scalaVersion in ThisBuild := "2.13.4"
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

val scalaJVMVersions = List("2.12.12", "2.11.12", "2.13.4", "3.0.0-M3")
val scalaJSVersions = List("2.12.12", "2.11.12", "2.13.4")

// Dependency versions
val scalatestVersion = "3.2.3"

lazy val root = project.in(file("."))
  .aggregate(
    coreJS, coreJVM, coreNative,
    unitJS, unitJVM, unitNative
  )
  .settings(
    name := "perfolation",
    publish := {},
    publishLocal := {}
  )

val commonNativeSettings = Seq(
  scalaVersion := "2.11.12",
  crossScalaVersions := Seq("2.11.12"),
  nativeLinkStubs := true,
  Test / test := {}
)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .settings(
    name := "perfolation"
  )
  .jvmSettings(
    crossScalaVersions := scalaJVMVersions,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jsSettings(
    crossScalaVersions := scalaJSVersions,
    test := {},                 // Temporary work-around for ScalaTest not working with Scala.js on Dotty
    libraryDependencies ++= (
      if (isDotty.value) {      // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        List("org.scalatest" %%% "scalatest" % scalatestVersion % "test")
      }
    )
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val coreJS = core.js
lazy val coreJVM = core.jvm
lazy val coreNative = core.native

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .dependsOn(core)
  .settings(
    name := "perfolation-unit"
  )
  .jvmSettings(
    crossScalaVersions := scalaJVMVersions,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jsSettings(
    crossScalaVersions := scalaJSVersions,
    test := {},                 // Temporary work-around for ScalaTest not working with Scala.js on Dotty
    libraryDependencies ++= (
      if (isDotty.value) {      // Temporary work-around for ScalaTest not working with Scala.js on Dotty
        Nil
      } else {
        List("org.scalatest" %%% "scalatest" % scalatestVersion % "test")
      }
    )
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val unitJS = unit.js
lazy val unitJVM = unit.jvm
lazy val unitNative = unit.native

lazy val benchmarks = project
  .in(file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .dependsOn(coreJVM)
  .settings(
    libraryDependencies ++= Seq(
      "pl.project13.scala" % "sbt-jmh-extras" % "0.3.7"
    ),
    crossScalaVersions := List("2.13.4")
  )
