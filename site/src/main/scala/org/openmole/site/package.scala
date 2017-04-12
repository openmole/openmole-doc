package org.openmole.site

import scalatags.JsDom.all._
import scaladget.stylesheet.all._
import scalatex.site.{ Highlighter, Section }

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

  lazy val mainDiv: ModifierSeq = Seq(
    scaladget.stylesheet.all.paddingTop(50),
    width := "50%",
    margin := "0 auto"
  )
}

package object tools {
  object sect extends Section()
  object hl extends Highlighter {
    override def suffixMappings = Map().withDefault(identity)
    def openmole(code: String, test: Boolean = true, header: String = "") = {
     // if (Config.testScript && test) DSLTest.test(code, header)
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
