/*
 * Copyright (C) 2015 Romain Reuillon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.site

import java.io.{File, FileInputStream}
import java.nio.CharBuffer
import java.nio.file.Paths
import java.util.zip.GZIPInputStream

import ammonite.ops.{Path, write}
//import org.openmole.core.serializer.SerializerService
//import org.openmole.core.workspace.Workspace
//import org.openmole.site.market.Market
import org.openmole.tool.file._
//import org.openmole.tool.tar._
//import org.openmole.tool.stream._

import scalatags.Text.all
import scalatags.Text.all._
//import scala.sys.process.BasicIO
//import org.openmole.site.credits._
//import spray.json.JsArray
//import module._
//import org.openmole.core.buildinfo
//import org.openmole.core.market.MarketIndex

import scala.annotation.tailrec

object Site extends App {

  def piwik =
    RawFrag(
      """
        |<!-- Piwik -->
        |<script type="text/javascript">
        |  var _paq = _paq || [];
        |  _paq.push(["setDocumentTitle", document.domain + "/" + document.title]);
        |  _paq.push(['trackPageView']);
        |  _paq.push(['enableLinkTracking']);
        |  (function() {
        |    var u="//piwik.iscpif.fr/";
        |    _paq.push(['setTrackerUrl', u+'piwik.php']);
        |    _paq.push(['setSiteId', 1]);
        |    _paq.push(['enableLinkTracking']);
        |    var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
        |    g.type='text/javascript'; g.async=true; g.defer=true; g.src=u+'piwik.js'; s.parentNode.insertBefore(g,s);
        |  })();
        |</script>
        |<noscript><p><img src="//piwik.iscpif.fr/piwik.php?idsite=1" style="border:0;" alt="" /></p></noscript>
        |<!-- End Piwik Code -->
      """.stripMargin
    )

  override def main(args: Array[String]): Unit = {
    case class Parameters(
                           target: Option[File] = None,
                           test: Boolean = false,
                           marketTest: Boolean = false,
                           resources: Option[File] = None,
                           ignored: List[String] = Nil
                         )

    @tailrec def parse(args: List[String], c: Parameters = Parameters()): Parameters = args match {
      case "--target" :: tail ⇒ parse(tail.tail, c.copy(target = tail.headOption.map(new File(_))))
      //  case "--test" :: tail        ⇒ parse(tail, c.copy(test = true))
      //case "--market-test" :: tail ⇒ parse(tail, c.copy(marketTest = true))
      case "--resources" :: tail ⇒ parse(tail.tail, c.copy(resources = tail.headOption.map(new File(_))))
      case s :: tail ⇒ parse(tail, c.copy(ignored = s :: c.ignored))
      case Nil ⇒ c
    }

    val parameters = parse(args.toList.map(_.trim))

    // Config.testScript = parameters.test

    val dest = parameters.target.getOrElse(new File("/tmp/openmole-site"))
    //dest.recursiveDelete

    //    val modules = generateModules(dest, f ⇒ s"modules/${f.getName}", dest / buildinfo.moduleListName)
    //    val marketEntries = generateMarket(parameters.resources.get, dest, dest / buildinfo.marketName, parameters.test && parameters.marketTest)

    // DocumentationPages.marketEntries = marketEntries

    case class PageFrag(page: Page, frag: Frag)

    def mdFiles = (parameters.resources.get / "md").listFilesSafe.filter(_.getName.endsWith(".md"))

    val site = new scalatex.site.Site {
      site ⇒
      override def siteCss = Set.empty

      override def pageTitle: Option[String] = None

      def headFrags(page: org.openmole.site.Page) =
        Seq(
          scalatags.Text.tags2.title(page.title),
          meta(name := "description", all.content := s"OpenMOLE: a workflow system for distributed computing and parameter tuning"),
          meta(name := "keywords", all.content := "Scientific Workflow Engine, Distributed Computing, Cluster, Grid, Parameter Tuning, Model Exploration, Design of Experiment, Sensitivity Analysis, Data Parallelism"),
          meta(name := "viewport", all.content := "width=device-width, initial-scale=1"),

          link(rel := "stylesheet", href := stylesName),
          link(rel := "stylesheet", href := Resource.bootstrapCss.file),
          //  link(rel := "stylesheet", href := Resource.css.file),
          link(rel := "stylesheet", href := Resource.github.file),
          link(rel := "stylesheet", href := Resource.docStyle.file),
          script(src := Resource.highlightJS.file),
          script(`type` :="text/javascript", src := Resource.siteJS.file),
         // script(src := Resource.lunr.file),
       //   script(src := Resource.index.file),
          meta(charset := "UTF-8")
        //  piwik
        )

   //   println("RRRRUUN " + JSCompiler.apply)
//
//      def ifDoc(page: org.openmole.site.Page, ID: String, IDelse: String, content: String) =
//      div(id := {
//        if (Pages.isDoc(page)) shared.sitexDoc else shared.sitexMain
//      }, page.content)

      /**
        * The body of this site's HTML page
        */
      def bodyFrag(page: org.openmole.site.Page) = body(
        div(id := shared.sitexIntro, page.intro.map{_.intro}.getOrElse("")),
        div(id := shared.sitexIntroMore, page.intro.map{_.more.getOrElse(RawFrag(""))}.getOrElse("")),
        div(id := {
          if (DocumentationPages.topPagesChildren.contains(page)) shared.sitexDoc else shared.sitexMain
        }, page.content),
        onload := "org.openmole.site.SiteJS().main();"
      )(`class` := "fade-in")

      override def generateHtml(outputRoot: Path) = {
        val res = Pages.all.map { page =>
          val txt = html(
            head(headFrags(page)),
            bodyFrag(bodyFrag(page))
          ).render
          val cb = CharBuffer.wrap("<!DOCTYPE html>" + txt)
          val bytes = scala.io.Codec.UTF8.encoder.encode(cb)
          val target = outputRoot / page.file
          write.over(target, bytes.array())
          //     LunrIndex.Index(path, txt)
        }

        //  write.over(outputRoot / "index.js", "var index = " + JsArray(res.toVector).compactPrint)
      }

      import scalaz._
      import Scalaz._

      lazy val pagesFrag = Pages.all.map {
        _.content
      } /*.toVector.traverseU { p ⇒ Pages.decorate(p).map(PageFrag(p, _)) }.run(new java.io.File("") /*parameters.resources.get*/)*/

      def content = Pages.all.map { p => p.file → (site.headFrags(p), p.content) }.toMap

      // def content = //pagesFrag.map { case PageFrag(p, f) ⇒ p.file → (site.headFrags(p), f) }.toMap

    }

    site.renderTo(Path(dest))
    //    lazy val bibPapers = Publication.papers ++ Communication.papers
    //    bibPapers foreach (_.generateBibtex(dest))
    //
    //    site.renderTo(Path(dest))
    //
    //    for {
    //      r ← Resource.all ++ Resource.marketResources(marketEntries)
    //    } r match {
    //      case RenameFileResource(source, destination) ⇒
    //        val from = parameters.resources.get / source
    //        val f = new File(dest, destination)
    //        from copy f
    //      case ArchiveResource(name, dir) ⇒
    //        val f = new File(dest, dir)
    //        f.mkdirs
    //        val resource = parameters.resources.get / name
    //        withClosable(new TarInputStream(new GZIPInputStream(new FileInputStream(resource)))) {
    //          _.extract(f)
    //        }
    //      case MarketResource(entry) ⇒
    //        val f = new File(dest, entry.entry.name)
    //        entry.location copy f
    //    }
    //
    //    DSLTest.runTest.get
    //
    //    0
    //  }
    //
    //  def generateModules(baseDirectory: File, moduleLocation: File ⇒ String, index: File) = {
    //    import org.json4s._
    //    import org.json4s.jackson.Serialization
    //    implicit val formats = Serialization.formats(NoTypeHints)
    //    val modules = module.generate(module.allModules, baseDirectory, moduleLocation)
    //    index.content = Serialization.writePretty(modules)
    //    modules
    //  }
    //
    //  def generateMarket(resourceDirectory: File, dest: File, index: File, test: Boolean) = {
    //    import org.json4s._
    //    import org.json4s.jackson.Serialization
    //    implicit val formats = Serialization.formats(NoTypeHints)
    //    val m = new Market(Market.entries, dest)
    //    val marketEntries = m.generate(resourceDirectory, test)
    //    index.content = Serialization.writePretty(MarketIndex(marketEntries.map(_.toDeployedMarketEntry)))
    //    marketEntries
    //  }

  }
}
