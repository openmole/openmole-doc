package org.openmole.site

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import bs._

@JSExport("site.Site")
object Site extends JSApp {

  @JSExport()
  def main(): Unit = {
    withBootstrapNative {

      def buildTabs(docPages: Seq[DocumentationPage]) = {
        val tabs = Tabs(sheet.pills)

        docPages.foldLeft(tabs)((tabs, t) => {
          val aDiv = tags.div.render
          raw(t.content.render).applyTo(aDiv)
          tabs.add(t.name, tags.div(aDiv))
        })
      }

      lazy val methodTabs = buildTabs(DocumentationPages.root.language.method.children)
      lazy val envTabs = buildTabs(DocumentationPages.root.language.environment.children)
      lazy val taskTabs = buildTabs(DocumentationPages.root.language.task.children)

      val carousel = new StepCarousel(
        Step("METHOD", methodTabs.render),
        Step("MODEL", taskTabs.render),
        Step("ENVIRONMENT ", envTabs.render)
      ).render

      val mainDiv = tags.div(sitesheet.mainDiv)(
        carousel
      )

      mainDiv.render
    }


    Highlighting.init
  }

}