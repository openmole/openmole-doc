/**
  * Created by mathieu on 10/05/17.
  */

import org.openmole.site._
import better.files._

object MacroSite extends App {

  override def main(args: Array[String]) = {

    val targetFile = File(args(0))
    targetFile.createIfNotExists()

    println("TARGET " + targetFile)

    val header =
      """
      package org.openmole.site

        trait JSPage {
          def file: String
          }
        case class JSMainPage(file: String) extends JSPage
        case class JSDocumentationPage(file: String) extends JSPage

      object JSPages {
      """.stripMargin

    val footer = "\n\n}"

    targetFile overwrite Pages.all.foldLeft(header) { (acc, page) =>
      println(page.location)
      val constr = page match {
        case dp: DocumentationPage => "JSDocumentationPage"
        case _ => "JSMainPage"
      }
      acc + s"""\nval ${page.location.mkString("_").replaceAll(" ", "_").replaceAll("-","_").toLowerCase} = $constr("${Pages.file(page)}")"""
    } + footer

  }
}