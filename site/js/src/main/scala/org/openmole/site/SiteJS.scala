package org.openmole.site

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scaladget.tools.JsRxTags._

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation._
import scalatags.JsDom.tags
import scalatags.JsDom.all._
import bs._
import rx._
/*
 * Copyright (C) 09/05/17 // mathieu.leclaire@openmole.org
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
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@JSExportTopLevel("site.SiteJS")
object SiteJS extends JSApp {

  @JSExport()
  def main(): Unit = {
    withBootstrapNative {



      val mainDiv = tags.div(
        Menu.build
        //          tags.div(sitesheet.mainDiv)(
        //            Rx {
        //              Menu.currentCatergory() match {
        //                case category.Documentation=> UserGuide.carousel.render
        //                case category.Home=> tags.div("Home").render
        //                case _=> tags.div("Else").render
        //              }
        //            }
        //          )
      )

      mainDiv.render
    }


   // Highlighting.init
  }
}