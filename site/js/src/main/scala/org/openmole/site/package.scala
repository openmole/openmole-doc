package org.openmole.site

import org.scalajs.dom.raw.HTMLDivElement
import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import bs._
import sheet._


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

  lazy val detailButtons: ModifierSeq = Seq(
    float := "left",
    fixedPosition,
    top := 500,
    scalatags.JsDom.all.marginLeft := -150
  )
}
