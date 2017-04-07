package site
import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import sheet._
import bs._

@JSExport("site.Site")
object Site extends JSApp {

  @JSExport()
  def main(): Unit = {
    div("OpenMOLE site")

  }
}