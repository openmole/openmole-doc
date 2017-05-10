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
import scalatags.JsDom.tags
import org.scalajs.dom.raw.HTMLDivElement
import scalatags.JsDom.all._
import sheet._
import bs._

object UserGuide {

//  private def buildTabs(docPages: Seq[DocumentationPage]) = {
//    val tabs = Tabs(sheet.pills)
//
//    docPages.foldLeft(tabs)((tabs, t) => {
//      val content: HTMLDivElement = t.content
//
//      val withDetails = div(
//        tags.div(sitesheet.detailButtons)(
//          for {
//            d <- t.details
//          } yield {
//            tags.div(sheet.paddingTop(10), bs.button(d.name, btn_danger).expandOnclick({
//              lazy val content: HTMLDivElement = d.content
//              tags.div(content)
//            }))
//          }),
//        content
//      )
//
//      tabs.add(t.name, withDetails)
//    })
//  }
//
//  private lazy val methodTabs = buildTabs(DocumentationPages.root.language.method.children)
//  private lazy val envTabs = buildTabs(DocumentationPages.root.language.environment.children)
//  private lazy val taskTabs = buildTabs(DocumentationPages.root.language.model.children)
//
//   lazy val carousel = new StepCarousel(
//    Step("1.MODEL", taskTabs.render, Seq(), DocumentationPages.root.language.model.intro),
//    Step("2.METHOD", methodTabs.render, Seq(),DocumentationPages.root.language.method.intro),
//    Step("3.ENVIRONMENT ", envTabs.render, Seq(), DocumentationPages.root.language.environment.intro)
//  ).render


}


