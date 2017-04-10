package org.openmole.site

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import org.openmole.site
import bs._

@JSExport("site.Site")
object Site extends JSApp {

  @JSExport()
  def main(): Unit =
    withBootstrapNative {

      val div1 = div("Lorem ipsum dolor sit amet, " +
        "consectetur adipiscing elit, sed do eiusmod tempor " +
        "incididunt ut labore et dolore magna aliqua. " +
        "Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
        "laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
        "irure dolor in reprehenderit in voluptate velit esse cillum dolore " +
        "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
        "sunt in culpa qui officia deserunt mollit anim id est laborum.", padding := 10)


      val div2 = vForm(bs.input("")(placeholder := "Name").render.withLabel("Your name"))

      val tabs = Tabs(sheet.pills).
        add("Java / Scala", div1).
        add("Docker", div2, true)


     val mainDiv = tags.div(site.sitesheet.mainDiv)(
       tabs.render
     )

      mainDiv.render
    }
}