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

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scalatags.JsDom.tags
import scalatags.JsDom.all._
import bs._

object UserGuide {

  private def buildTabs(docPages: Seq[DocumentationPage]) = {
    val tabs = Tabs(sheet.pills)

    docPages.foldLeft(tabs)((tabs, t) => {
      val aDiv = tags.div.render
      raw(t.content.render).applyTo(aDiv)
      tabs.add(t.name, tags.div(aDiv))
    })
  }

  private lazy val methodTabs = buildTabs(DocumentationPages.root.language.method.children)
  private lazy val envTabs = buildTabs(DocumentationPages.root.language.environment.children)
  private lazy val taskTabs = buildTabs(DocumentationPages.root.language.task.children)

  lazy val carousel = new StepCarousel(
    Step("METHOD", methodTabs.render),
    Step("MODEL", taskTabs.render),
    Step("ENVIRONMENT ", envTabs.render)
  ).render
}
