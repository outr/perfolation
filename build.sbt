import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

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
val scalatestVersion = "3.2.0-M3"
val testInterfaceVersion = "0.4.0-M2"

val commonNativeSettings = Seq(
  scalaVersion := "2.11.12",
  crossScalaVersions := Seq("2.11.12"),
  nativeLinkStubs := true,
  test := {}
)

lazy val macros = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .settings(
    name := "perfolation-macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    publishArtifact in Test := false
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .dependsOn(macros)
  .settings(
    name := "perfolation",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % "test"
    )
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val coreJVM = core.jvm

lazy val unit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .dependsOn(core)
  .settings(
    name := "perfolation-unit",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % Test
    )
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val benchmarks = project
  .in(file("benchmarks"))
  .enablePlugins(JmhPlugin)
  .dependsOn(coreJVM)
  .settings(
    libraryDependencies ++= Seq(
      "pl.project13.scala" % "sbt-jmh-extras" % "0.3.7"
    )
  )
