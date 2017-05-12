package org.openmole.site

/*
 * Copyright (C) 19/04/17 // mathieu.leclaire@openmole.org
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

import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scaladget.tools.JsRxTags._
import scalatags.JsDom.tags
import org.scalajs.dom.raw.HTMLDivElement

import scalatags.JsDom.all._
import sheet._
import bs._

object UserGuide {

  val replacer = utils.replacer

  private def buildTabs(docPages: Seq[JSDocumentationPage]) = {
    val tabs = Tabs(sheet.pills)

    docPages.foldLeft(tabs)((tabs, t) => {

      val withDetails = tags.div(
        tags.div(sitesheet.detailButtons)(
          for {
            d <- t.details
          } yield {
            tags.div(sheet.paddingTop(10), bs.button(d.name, btn_danger))
          }),
        replacer.tag
      )

      tabs.add(t.name, withDetails)
    })
  }

  def addCarousel = {

    val methodTabs = buildTabs(JSPages.documentation_language_methods.children)
    val envTabs = buildTabs(JSPages.documentation_language_environments.children)
    val taskTabs = buildTabs(JSPages.documentation_language_models.children)


    val carrousel = tags.div(sitesheet.mainDiv)(
      new StepCarousel(
        Step("1.MODEL", taskTabs.render, Seq()),
        Step("2.METHOD", methodTabs.render, Seq()),
        Step("3.ENVIRONMENT ", envTabs.render, Seq())
      ).render
    )

    org.scalajs.dom.window.document.body.appendChild(carrousel)
    replacer.replaceWith(shared.sitexDoc)
  }


}


