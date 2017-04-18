package org.openmole.site

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import scalatex.openmole.documentation.{language ⇒ language}
import bs._

@JSExport("site.Site")
object Site extends JSApp {

  @JSExport()
  def main(): Unit = {
    withBootstrapNative {


      val div1 = tags.div.render
      raw(language.task.Scala().render).applyTo(div1)

      val div2 = vForm(bs.input("")(placeholder := "Name").render.withLabel("Your name"))

      val tabs = Tabs(sheet.pills).
        add("Java / Scala", tags.div(div1), true).
        add("Docker", div2)

      val carousel = new StepCarousel(
        Step("MODEL", tabs.render),
        Step("METHOD", tabs.render),
        Step("ENVIRONMENT ", tabs.render)
      )

      val mainDiv = tags.div(sitesheet.mainDiv)(
        carousel.render
      )

      mainDiv.render
    }


    Highlighting.init
  }

}