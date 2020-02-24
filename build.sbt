import sbtcrossproject.CrossPlugin.autoImport.crossProject

name := "perfolation"
organization in ThisBuild := "com.outr"
version in ThisBuild := "1.1.6"
scalaVersion in ThisBuild := "2.13.1"
crossScalaVersions in ThisBuild := List("2.12.10", "2.11.12", "2.13.1")
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
val scalatestVersion = "3.2.0-M2"
val scalacheckVersion = "1.14.3"
val testInterfaceVersion = "0.4.0-M2"

lazy val macros = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("macros"))
  .settings(
    name := "perfolation-macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    publishArtifact in Test := false
  )
  .nativeSettings(
    scalaVersion := "2.11.12",
  )

lazy val macrosJS = macros.js
lazy val macrosJVM = macros.jvm
lazy val macrosNative = macros.native

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("core"))
  .dependsOn(macros)
  .settings(
    name := "perfolation",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
    )
  )
  .nativeSettings(
    nativeLinkStubs := true,
    libraryDependencies ++= Seq(
      "org.scala-native" %%% "test-interface" % testInterfaceVersion
    ),
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.11.12")
  )

lazy val coreJS = core.js
lazy val coreJVM = core.jvm
lazy val coreNative = core.native

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("unit"))
  .dependsOn(core)
  .settings(
    name := "perfolation-unit",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
    )
  )
  .nativeSettings(
    nativeLinkStubs := true,
    libraryDependencies ++= Seq(
      "org.scala-native" %%% "test-interface" % testInterfaceVersion
    ),
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.11.12")
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
    )
  )