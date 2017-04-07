import sbt._
import Keys._

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

val ScalaVersion = "2.11.8"
val bootstrapNativeVersion = "1.1.0"
val scalatagsVersion = "0.6.3"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.1"
val scaladgetVersion = "0.9.3-SNAPSHOT"
val sourceCodeVersion = "0.1.2"

lazy val defaultSettings = Seq(
  organization := "org.openmole",
  version := "0.1-SNAPSHOT",
  scalaVersion := ScalaVersion,
  resolvers := Seq(Resolver.sonatypeRepo("snapshots"),
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
  homepage := Some(url("https://github.com/openmole/openmole-doc")),
  scmInfo := Some(ScmInfo(url("https://github.com/openmole/openmole-doc.git"), "scm:git:git@github.com:openmole/openmole-doc.git")),
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

lazy val site = project.in(file("site"))  enablePlugins (ScalaJSPlugin) settings (defaultSettings: _*) settings(
  libraryDependencies += "fr.iscpif" %%% "scaladget" % scaladgetVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion,
  buildSite := {
    val siteTarget = target.value
    val siteResource = (resourceDirectory in Compile).value
    //  val bootstrapJS = (npmUpdate in Compile).value / "node_modules" / "bootstrap.native" / "dist" / "bootstrap-native.min.js"
    val siteJS = (fullOptJS in Compile).value

    IO.copyFile(siteJS.data, siteTarget / "js/site.js")
    //  IO.copyFile(bootstrapJS, siteTarget / "js/bootstrap.-native.min.js")


    IO.copyFile(siteResource / "index.html", siteTarget / "index.html")
    IO.copyDirectory(siteResource / "js", siteTarget / "js")
    IO.copyDirectory(siteResource / "css", siteTarget / "css")
    IO.copyDirectory(siteResource / "fonts", siteTarget / "fonts")
  }
)