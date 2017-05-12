package org.openmole.site

import org.openmole.site.sitesheet._
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scaladget.tools.JsRxTags._
import rx._

import sheet._

/*
 * Copyright (C) 14/04/17 // mathieu.leclaire@openmole.org
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
case class Step(name: String, element: HTMLElement, page: JSDocumentationPage)

class StepCarousel(current: Int, steps: Step*) {
  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  val currentStep = steps(current)
  val stepsSize = steps.size

  def toRight = Menu.to(steps((current + 1) % stepsSize).page)
  def toLeft = Menu.to(steps((current + stepsSize - 1) % stepsSize).page)

  val render = tags.div(
    Rx {
      val intro: HTMLDivElement = div("intro ...").render //step.intro
      tags.div(
      tags.div(width := "100%")(
        tags.div(marginAuto)(
          bs.glyphSpan(glyph_chevron_left +++ sitesheet.stepHeader +++ floatLeft, () => toLeft),
          span(currentStep.name, sitesheet.stepHeader +++ sitesheet.marginAuto),
          bs.glyphSpan(glyph_chevron_right +++ sitesheet.stepHeader +++ floatRight, () => toRight)
        )),
      div(sheet.paddingTop(25), "intro"),
      div(sheet.paddingTop(600))(currentStep.element)
      )
    }
  )
}
