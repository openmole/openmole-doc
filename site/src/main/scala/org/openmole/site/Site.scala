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

      def buildTabs(docPages: Seq[DocumentationPage]) =
        docPages.foldLeft(Tabs(sheet.pills))((tabs, t) => {
          val aDiv = tags.div.render
          raw(t.content.render).applyTo(aDiv)
          tabs.add(t.name, tags.div(aDiv))
        })

      val taskTabs = buildTabs(DocumentationPages.root.language.task.children)
      val methodTabs = Tabs(sheet.pills)
      val envTabs = buildTabs(DocumentationPages.root.language.environment.children)

      val carousel = new StepCarousel(
        Step("MODEL", taskTabs.render),
        Step("METHOD", methodTabs.render),
        Step("ENVIRONMENT ", envTabs.render)
      )

      val mainDiv = tags.div(sitesheet.mainDiv)(
        carousel.render
      )

      mainDiv.render
    }


    Highlighting.init
  }

}