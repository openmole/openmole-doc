import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

val ScalaVersion = "2.11.8"
val scalatagsVersion = "0.6.5"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.1"
val scaladgetVersion = "0.9.5-SNAPSHOT"

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

lazy val buildSite = inputKey[Unit]("buildSite")
lazy val buildMacro = inputKey[Unit]("buildMacro")

lazy val macrosite = project.in(file("macrosite")) settings (defaultSettings: _*) settings (
  libraryDependencies += "com.github.pathikrit" %% "better-files" % "2.17.1"
  ) dependsOn (siteJVM)

lazy val site = crossProject settings (defaultSettings: _*) settings (scalatex.SbtPlugin.projectSettings
  ) settings(
  libraryDependencies += "org.scalaz" %%% "scalaz-core" % "7.2.10",
  libraryDependencies += "com.lihaoyi" %%% "scalatags" % scalatagsVersion
) jvmSettings(
  libraryDependencies += "com.lihaoyi" %% "scalatex-site" % "0.3.7",
  libraryDependencies += "org.openmole" %% "org-openmole-tool-file" % "7.0-SNAPSHOT"
) jsSettings(
  libraryDependencies += "fr.iscpif" %%% "scaladget" % scaladgetVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.github.karasiq" %%% "scalajs-marked" % "1.0.2",
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion
)


lazy val siteJS = site.js
lazy val siteJVM = site.jvm

buildMacro := {

  Def.inputTaskDyn {
    Def.taskDyn {
      val jsSource = (sourceDirectory in siteJS in Compile).value / "scala/org/openmole/site/macropackage.scala"
      println("js " + jsSource)
      (run in macrosite in Compile).toTask(" " + jsSource).result
    }
  }.evaluated
}

buildSite := {
  buildMacro
  (Def.inputTaskDyn {
    import sbt.complete.Parsers.spaceDelimited

    val parsed = spaceDelimited("<args>").parsed

    val defaultDest = (target in siteJVM).value
    val (siteTarget, args) = parsed.indexOf("--target") match {
      case -1 => (defaultDest, parsed ++ Seq("--target", defaultDest.getAbsolutePath))
      case i: Int => {
        if (i == parsed.size - 1) (defaultDest, parsed :+ defaultDest.getAbsolutePath)
        else (file(parsed(i + 1)), parsed)
      }
    }

    Def.taskDyn {
      val siterun = (run in siteJVM in Compile).toTask(" " + args.mkString(" ")).result
      val siteResource = (resourceDirectory in siteJS in Compile).value
      val siteBuildJS = (fullOptJS in siteJS in Compile).value

      IO.copyFile(siteBuildJS.data, siteTarget / "js/sitejs.js")
      IO.copyDirectory(siteResource / "js", siteTarget / "js")
      IO.copyDirectory(siteResource / "css", siteTarget / "css")
      IO.copyDirectory(siteResource / "fonts", siteTarget / "fonts")
      IO.copyDirectory(siteResource / "img", siteTarget / "img")
      siterun
    }

  }).evaluated
}