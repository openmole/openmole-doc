import sbt._
import Keys._

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

val ScalaVersion = "2.11.8"
val scalatagsVersion = "0.6.3"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.1"
val scaladgetVersion = "0.9.4-SNAPSHOT"

lazy val defaultSettings = Seq(
  organization := "org.openmole",
  version := "0.1-SNAPSHOT",
  scalaVersion := ScalaVersion,
  resolvers := Seq(Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    DefaultMavenRepository
  ),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomIncludeRepository := { _ => false },
  licenses := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/")),
  homepage := Some(url("https://github.com/openmole/openmole-site")),
  scmInfo := Some(ScmInfo(url("https://github.com/openmole/openmole-site.git"), "scm:git:git@github.com:openmole/openmole-site.git")),
  pomExtra := {
    <developers>
      <developer>
        <id>mathieuleclaire</id>
        <name>Mathieu Leclaire</name>
        <url>https://github.com/mathieuleclaire/</url>
      </developer>
    </developers>
  }
)

lazy val buildSite = taskKey[Unit]("buildSite")

lazy val site = project.in(file("site")) enablePlugins (ScalaJSPlugin) settings (defaultSettings: _*) settings(scalatex.SbtPlugin.projectSettings) settings(
  libraryDependencies += "fr.iscpif" %%% "scaladget" % scaladgetVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalatex-site" % "0.3.7",
  libraryDependencies += "com.github.karasiq" %%% "scalajs-marked" % "1.0.3-SNAPSHOT",
  libraryDependencies += "org.scalaz" %%% "scalaz-core" % "7.2.10",
  buildSite := {
    val siteTarget = target.value
    val siteResource = (resourceDirectory in Compile).value
    val siteJS = (fullOptJS in Compile).value

    IO.copyFile(siteJS.data, siteTarget / "js/site.js")
    IO.copyFile(siteResource / "index.html", siteTarget / "openmole.html")
    IO.copyDirectory(siteResource / "js", siteTarget / "js")
    IO.copyDirectory(siteResource / "css", siteTarget / "css")
    IO.copyDirectory(siteResource / "fonts", siteTarget / "fonts")
  }
)

lazy val content = project.in(file("content")) settings (defaultSettings: _*)
