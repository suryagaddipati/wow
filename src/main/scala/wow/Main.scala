package wow


import wow.aws._

import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Main {
  def main(args: Array[String]) {
    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile("/home/surya/code/wow/src/main/examples/oneInstanceWithEip.scala").getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    toolbox.compile(tree)
    val k = toolbox.eval(tree)
    AWS.create

  }

}