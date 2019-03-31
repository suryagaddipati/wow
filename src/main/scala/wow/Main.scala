package wow


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox


object Main {
  def main(args: Array[String]) {
    val toolbox = currentMirror.mkToolBox()
    //    println(AWS.ec2)

    val fileContents = Source.fromFile("/home/surya/code/wow/src/main/examples/ec2.scala").getLines.mkString("\n")
    val tree = toolbox.parse(fileContents)
    toolbox.compile(tree)
    val k = toolbox.eval(tree)
    println(k)

  }

}