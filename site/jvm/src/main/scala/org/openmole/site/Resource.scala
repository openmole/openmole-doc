/*
 * Copyright (C) 2015 Romain Reuillon
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
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.site

//import org.openmole.core.buildinfo
//import org.openmole.site.market.GeneratedMarketEntry
//import org.openmole.site.market.Market._

//TODO automatically generate this object as a managed source using sbt
object Resource {

  //FIXME
  def buildinfoVersion = "7.0-SNAPSHOT"

  def FileResource(name: String) = RenameFileResource(name, name)

  //def css = FileResource("openMOLEStyles.css")

  def ants = FileResource("ants.png")

  def antNumbers = FileResource("antnumbers.png")

  def antsNLogo = FileResource("ants.nlogo")

  def bootstrapCss = FileResource("css/bootstrap.min-3.3.7.css")

  def github = FileResource("css/github.css")

  def docStyle = FileResource("css/docstyle.css")

  def bootstrapJS = FileResource("js/bootstrap-native.min.js")

  def highlightJS = FileResource("js/highlight.pack.js")

  def siteJS = FileResource("js/sitejs.js")

  def care = FileResource("care")

  def fireNLogo = FileResource("Fire.nlogo")

  def fireScreen = FileResource("firescreen.png")

  def fireGlobals = FileResource("fireGlobals.png")

  def fireNewGlobals = FileResource("fireNewGlobals.png")

  def fireMyDensity = FileResource("fireMyDensity.png")

  def fireNewFunction = FileResource("fireNewFunction.png")

  def fireOldSetup = FileResource("fireOldSetup.png")

  def fireRemoveClearAll = FileResource("fireRemoveClearAll.png")

  def logo = FileResource("openmole.png")

  def uiScreenshot = FileResource("openmoleUI.png")

  def iscpif = FileResource("iscpif.svg")

  def geocite = FileResource("geocite.png")

  def biomedia = FileResource("biomedia.png")

  def img = FileResource("img")

  def openmole = RenameFileResource("openmole.tar.gz", s"openmole-${buildinfoVersion}.tar.gz")

  def openmoleDaemon = RenameFileResource("openmole-daemon.tar.gz", s"openmole-daemon-${buildinfoVersion}.tar.gz")

  def api = ArchiveResource("openmole-api.tar.gz", "api")

  def lunr = FileResource("lunr.min.js")

  def index = FileResource("index.js")

 // def marketResources(entries: Seq[GeneratedMarketEntry]) =
  //  entries.filter(_.tags.exists(_ == market.Market.Tags.tutorial)).map { tuto ⇒ MarketResource(tuto) }

  def all = Seq[Resource](
    docStyle,
    github,
    highlightJS,
    siteJS,
    bootstrapCss,
    bootstrapJS,
    logo,
    openmole,
    openmoleDaemon,
    api,
    ants,
    antNumbers,
    antsNLogo,
    fireNLogo,
    fireScreen,
    fireGlobals,
    fireNewGlobals,
    fireNewFunction,
    fireOldSetup,
    fireRemoveClearAll,
    uiScreenshot,
    iscpif,
    geocite,
    biomedia,
    img,
    lunr,
    care
  )
}

sealed trait Resource
case class RenameFileResource(source: String, file: String) extends Resource
case class ArchiveResource(source: String, file: String) extends Resource
//case class MarketResource(marketEntry: GeneratedMarketEntry) extends Resource

