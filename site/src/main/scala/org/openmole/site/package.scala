package org.openmole.site

import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

import scalatags.JsDom.TypedTag


/*
 * Copyright (C) 01/04/16 // mathieu.leclaire@openmole.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY, without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package object sitesheet {

  import scalatags.JsDom.all._
  import scaladget.stylesheet.all._

  lazy val mainDiv: ModifierSeq = Seq(
    scaladget.stylesheet.all.paddingTop(100),
    width := "50%",
    margin := "0 auto"
  )

  lazy val stepHeader: ModifierSeq = Seq(
    width := 300,
    fontSize := 25,
    fontWeight := 800
  )

  lazy val marginAuto: ModifierSeq = Seq(
    margin := "0 auto"
  )
}

package object tools {

  import scalatags.Text.all._
  import scalatex.site.{Highlighter, Section}

  object sect extends Section()

  object hl extends Highlighter {
    override def suffixMappings = Map().withDefault(identity)

    def openmole(code: String, test: Boolean = true, header: String = "") = {
      highlight(code, "scala")
    }


    def openmoleNoTest(code: String) = openmole(code, test = false)
  }

  case class Parameter(name: String, `type`: String, description: String)

  def parameters(p: Parameter*) = {
    def toRow(p: Parameter) = li(p.name + ": " + p.`type` + ": " + p.description)

    ul(p.map(toRow))
  }

  def tq = """""""""

}

package object utils {

  import scalatags.JsDom.all.raw
  import scaladget.api.{BootstrapTags => bs}
  import scaladget.stylesheet.{all => sheet}
  import scalatags.JsDom.tags
  import scalatags.JsDom.all._

  implicit def texToDiv(textFrag: scalatags.Text.all.Frag): HTMLDivElement = {
    val aDiv = tags.div(sheet.paddingTop(60)).render
    raw(textFrag.render).applyTo(aDiv)
    aDiv
  }

  implicit def SomeTexToDiv(textFrag: Option[scalatags.Text.all.Frag]): HTMLDivElement = {
    textFrag.map(t => texToDiv(t)).getOrElse(div().render)
  }
}
