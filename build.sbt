import sbtcrossproject.{CrossType, crossProject}

name := "perfolation"
organization in ThisBuild := "com.outr"
version in ThisBuild := "1.0.2-SNAPSHOT"
scalaVersion in ThisBuild := "2.12.5"
crossScalaVersions in ThisBuild := List("2.12.5", "2.11.12")
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

lazy val macros = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
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
  .crossType(CrossType.Full)
  .in(file("core"))
  .dependsOn(macros)
  .settings(
    name := "perfolation",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalatest" %%% "scalatest" % "3.2.0-SNAP10" % "test"
    )
  )
  .nativeSettings(
    scalaVersion := "2.11.12"
  )

lazy val coreJS = core.js
lazy val coreJVM = core.jvm
lazy val coreNative = core.native
