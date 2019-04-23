package surya.wow

import sbt.Logger
import surya.wow.provider.aws.AWS

import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Commands {
  def create(log: Logger, file: String) = {
    log.info(s"Creating ${file}")

    val toolbox = currentMirror.mkToolBox()

    val fileContents = Source.fromFile(file).getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    toolbox.compile(tree)
    val k = toolbox.eval(tree)
    //    AWS.create
  }
}
