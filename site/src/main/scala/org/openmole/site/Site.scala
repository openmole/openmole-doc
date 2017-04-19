package org.openmole.site

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scaladget.tools.JsRxTags._

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.tags
import scalatags.JsDom.all._
import bs._
import rx._

@JSExport("site.Site")
object Site extends JSApp {

  @JSExport()
  def main(): Unit = {
    withBootstrapNative {



      val mainDiv = tags.div(
        Menu.build,
        tags.div(sitesheet.mainDiv)(
          Rx {
            Menu.currentCatergory() match {
              case category.Documentation=> UserGuide.carousel.render
              case category.Home=> tags.div("Home").render
              case _=> tags.div("Else").render
            }
          }
        )
      )

      mainDiv.render
    }


    Highlighting.init
  }

}