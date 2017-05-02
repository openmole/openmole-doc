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

import scaladget.stylesheet.{all => sheet}
import scaladget.api.{BootstrapTags => bs}
import scalatags.JsDom.all._
import sheet._
import bs._
import rx._

object Menu {

  val currentCatergory: Var[category.Category] = Var(category.Home)

  def build = {
    val docItem = stringNavItem("DOCUMENTATION", () ⇒
      currentCatergory() = category.Documentation
    )

    val downloadItem = stringNavItem("DOWNLOAD", () ⇒
      currentCatergory() = category.Download
    )

    val faqItem = stringNavItem("FAQ", () ⇒
      currentCatergory() = category.Faq
    )

    val demoItem = navItem(
      bs.linkButton("DEMO", "http://demo.openmole.org", btn_primary).render
    )


    val searchItem = navItem(
      bs.input("")(placeholder := "Search", width := 150).render, () ⇒
        println("Search")
    )

    val issueItem = navItem(
      bs.linkButton("ISSUES", "http://discourse.iscpif.fr", btn_primary).render,
      extraRenderPair = navbar_right
    )

    //Create the nav bar
    bs.navBar(
      navbar_staticTop +++ navbar_fixedTop +++ navbar_inverse +++ sheet.paddingRight(20),
      docItem.right,
      faqItem.right,
      downloadItem.right,
      searchItem.right,
      demoItem.right,
      issueItem.right
    ).render
  }
}